package com.jcohy.boot.jpa;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * 描述: 传统的阻塞式存储库适配器.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2022/5/18:00:54
 * @since 2022.04.0
 */
public abstract class ReactiveCrudRepositoryAdapter<T, ID, I extends CrudRepository<T, ID>>
        implements ReactiveCrudRepository<T, ID> {

    protected final I delegate;

    /**
     * 适配器需要 Scheduler 实例来转移来自事件的循环请求，调度程序的并行性定义了并打请求的数量.
     *
     */
    protected final Scheduler scheduler;

    public ReactiveCrudRepositoryAdapter(I delegate, Scheduler scheduler) {
        this.delegate = delegate;
        this.scheduler = scheduler;
    }

    @Override
    public <S extends T> Mono<S> save(S entity) {
        // 将阻塞式 sava 方法包装到 Mono.fromCallable() 并转移到 scheduler
        return Mono.fromCallable(() -> delegate.save(entity)).subscribeOn(scheduler);
    }

    @Override
    public <S extends T> Flux<S> saveAll(Iterable<S> entities) {
        return Mono.fromCallable(() -> delegate.saveAll(entities)).flatMapMany(Flux::fromIterable)
                .subscribeOn(scheduler);
    }

    @Override
    public <S extends T> Flux<S> saveAll(Publisher<S> entityStream) {
        return Flux.from(entityStream).flatMap(entity -> Mono.fromCallable(() -> delegate.save(entity)))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<T> findById(ID id) {
        return Mono.fromCallable(() -> delegate.findById(id))
                .flatMap(result -> result.map(Mono::just).orElseGet(Mono::empty)).subscribeOn(scheduler);
    }

    @Override
    public Mono<T> findById(Publisher<ID> id) {
        return Mono.from(id).flatMap(actualId -> delegate.findById(actualId).map(Mono::just).orElseGet(Mono::empty))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Boolean> existsById(ID id) {
        return Mono.fromCallable(() -> delegate.existsById(id)).subscribeOn(scheduler);
    }

    @Override
    public Mono<Boolean> existsById(Publisher<ID> id) {
        return Mono.from(id).flatMap(actualId -> Mono.fromCallable(() -> delegate.existsById(actualId)))
                .subscribeOn(scheduler);
    }

    @Override
    public Flux<T> findAll() {
        return Mono.fromCallable(delegate::findAll).flatMapMany(Flux::fromIterable).subscribeOn(scheduler);
    }

    @Override
    public Flux<T> findAllById(Iterable<ID> ids) {
        return Mono.fromCallable(() -> delegate.findAllById(ids)).flatMapMany(Flux::fromIterable)
                .subscribeOn(scheduler);
    }

    @Override
    public Flux<T> findAllById(Publisher<ID> idStream) {
        return Flux.from(idStream).buffer().flatMap(ids -> Flux.fromIterable(delegate.findAllById(ids)))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Long> count() {
        return Mono.fromCallable(delegate::count).subscribeOn(scheduler);
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return Mono.<Void>fromRunnable(() -> delegate.deleteById(id)).subscribeOn(scheduler);
    }

    @Override
    public Mono<Void> deleteById(Publisher<ID> id) {
        return Mono.from(id).flatMap(
                actualId -> Mono.<Void>fromRunnable(() -> delegate.deleteById(actualId)).subscribeOn(scheduler));
    }

    @Override
    public Mono<Void> delete(T entity) {
        return Mono.<Void>fromRunnable(() -> delegate.delete(entity)).subscribeOn(scheduler);
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends T> entities) {
        return Mono.<Void>fromRunnable(() -> delegate.deleteAll(entities)).subscribeOn(scheduler);
    }

    /**
     * deleteAll 的响应式适配器，由于 deleteAll(Iterable<? extends T> entities) 和
     * deleteAll(Publisher<? extends T> entityStream) 具有不同的语义，我们无法将一个响应式调用直接映射到一个阻塞式调用。
     * deleteAll 方法订阅实体并为每个实体发出单独的 delegate.delete(entity) 请求，由于删除请求可以并行进行，因此每个请求都有自己的
     * subscribeOn 调用，从 scheduler 接收工作单元。 deleteAll 方法返回一个输出流，该输出流在传入流终止且所有删除惭怍完成时完成。
     * @param entityStream must not be {@literal null}.
     * @return
     */
    @Override
    public Mono<Void> deleteAll(Publisher<? extends T> entityStream) {
        return Flux.from(entityStream)
                .flatMap(entity -> Mono.fromRunnable(() -> delegate.delete(entity)).subscribeOn(scheduler)).then();
    }

    @Override
    public Mono<Void> deleteAll() {
        return Mono.<Void>fromRunnable(delegate::deleteAll).subscribeOn(scheduler);
    }

}

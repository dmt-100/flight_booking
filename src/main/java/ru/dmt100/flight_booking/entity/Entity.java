package ru.dmt100.flight_booking.entity;

public class Entity<K, TDto> implements KeyProvider<K, TDto>{
    private K k;

//    public Entity(K k) {
//        this.k = k;
//    }

    @Override
    public K getKey(TDto tDto) {
        return k;
    }
}

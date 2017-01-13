package com.showyourtrace.util;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class DummyPageable implements Pageable {

    private int pageSize;

    private DummyPageable() {};
    private DummyPageable(int pageSize) { this.pageSize = pageSize; };

    public static DummyPageable getInstance(int pageSize) {
        DummyPageable newInstance = new DummyPageable(pageSize);
        return newInstance;
    }

    public int getPageNumber() {
        return 0;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getOffset() {
        return 0;
    }

    public Sort getSort() {
        return null;
    }

    public Pageable next() {
        return null;
    }

    public Pageable previousOrFirst() {
        return null;
    }

    public Pageable first() {
        return null;
    }

    public boolean hasPrevious() {
        return false;
    }
}

package com.languagelearn.object;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility =  JsonAutoDetect.Visibility.NON_PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageableRequest implements Pageable {
    public static final int PAGE_SIZE = 25;

    private int page;
    private int size;
    private List<Sort> sorts;

    public PageableRequest() {
        this.page = 1;
        this.size = PAGE_SIZE;
        this.sorts = Collections.EMPTY_LIST;
    }

    public PageableRequest(int page, int size, List<Sort> sorts) {
        this.page = page;
        this.size = size;
        this.sorts = sorts;
    }

    public PageableRequest(int page, int size, Sort[] sorts) {
        this.page = page;
        this.size = size;
        this.sorts = new ArrayList<Sort>(sorts.length);
        for(Sort s : sorts) {
            this.sorts.add(s);
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page != null ? page : 1;
    }

    public int getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size != null ? size : 25;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    //================================================================================================================

    public int getPageSize() {
        return size;
    }

    public int getPageNumber() {
        return page;
    }

    public int getOffset() {
        return (page - 1) * size;
    }

    public org.springframework.data.domain.Sort getSort() {
        List<org.springframework.data.domain.Sort.Order> orders = new ArrayList<org.springframework.data.domain.Sort.Order>(sorts.size());
        for(Sort sort : sorts) {
            if(sort.getProperty() != null && !sort.getProperty().isEmpty()) {
                org.springframework.data.domain.Sort.Order o = new org.springframework.data.domain.Sort.Order(sort.getSortDirection(), sort.getProperty());
                orders.add(o);
            }
        }
        if(orders.size() > 0) {
            return new org.springframework.data.domain.Sort(orders);
        }
        return null;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Page{");
        sb.append("page=").append(page);
        sb.append(", size=").append(size);
        sb.append(", sorts=").append(sorts);
        sb.append('}');
        return sb.toString();
    }

	@Override
	public Pageable next() {
		return null;
	}

	@Override
	public Pageable previousOrFirst() {
		return null;
	}

	@Override
	public Pageable first() {
		return null;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}
}

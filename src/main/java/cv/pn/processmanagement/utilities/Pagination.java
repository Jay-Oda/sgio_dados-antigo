package cv.pn.processmanagement.utilities;

public class Pagination {
    private int page;
    private int pageSize;
    private int pageCount;
    private long total;

    public Pagination(int page, int pageSize, int pageCount, long total) {
        this.page = page;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public long getTotal() {
        return total;
    }
}

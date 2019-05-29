package cn.ipanda.aigou.query;

public class BaseQuery {
    //query 作为查询：keyword,page,rows;
    private String keyword;                 //关键字
    private Integer page = 1;               //当前页(page-1)*size
    private Integer rows = 10;              //每页条数
    private Integer start=0;                //从哪里开始
    public Integer getStart() {
        return (this.page-1)*this.rows;     //(page-1)*size
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}

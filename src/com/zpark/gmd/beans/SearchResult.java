package com.zpark.gmd.beans;

/**
 * 搜索结果实体类
 */
public class SearchResult {
    private String type;        // 类型：employee, product, customer
    private String title;       // 标题
    private String content;     // 内容描述
    private String url;         // 跳转链接
    private Object data;        // 原始数据对象
    
    public SearchResult() {}
    
    public SearchResult(String type, String title, String content, String url, Object data) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.url = url;
        this.data = data;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    /**
     * 获取类型的中文名称
     */
    public String getTypeName() {
        switch (type) {
            case "employee":
                return "员工";
            case "product":
                return "产品";
            case "customer":
                return "客户";
            default:
                return "未知";
        }
    }
    
    /**
     * 获取类型对应的图标
     */
    public String getTypeIcon() {
        switch (type) {
            case "employee":
                return "users";
            case "product":
                return "package";
            case "customer":
                return "user-check";
            default:
                return "file";
        }
    }
}

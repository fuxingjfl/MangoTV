package com.xha.mangotv.util;

public enum  AppMenu {

    CHANNELVIEWINGTREND("频道收视走势",21100),
    CHANNELRANKING("频道收视排行",21000),
    CHANNELRATINGSHARE("频道分类份额占比",23000),
    REALTIME("实时收视",10000),
    LIVEPROGRAMVIEW("直播节目收视统计",22000)
    ;
    private String name;
    private Integer menuId;

    AppMenu(String name,Integer menuId){
        this.name = name;
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
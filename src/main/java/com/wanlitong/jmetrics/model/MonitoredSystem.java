package com.wanlitong.jmetrics.model;

import java.util.Date;

public class MonitoredSystem {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitored_system.system_id
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    private Long systemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitored_system.name
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitored_system.description
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitored_system.db_product
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    private String dbProduct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitored_system.db_ip
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    private String dbIp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitored_system.db_port
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    private Short dbPort;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitored_system.db_user
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    private String dbUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitored_system.db_pwd
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    private String dbPwd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitored_system.schema
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    private String schema;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitored_system.create_time
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitored_system.update_time
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitored_system.system_id
     *
     * @return the value of monitored_system.system_id
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public Long getSystemId() {
        return systemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitored_system.system_id
     *
     * @param systemId the value for monitored_system.system_id
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitored_system.name
     *
     * @return the value of monitored_system.name
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitored_system.name
     *
     * @param name the value for monitored_system.name
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitored_system.description
     *
     * @return the value of monitored_system.description
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitored_system.description
     *
     * @param description the value for monitored_system.description
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitored_system.db_product
     *
     * @return the value of monitored_system.db_product
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public String getDbProduct() {
        return dbProduct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitored_system.db_product
     *
     * @param dbProduct the value for monitored_system.db_product
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public void setDbProduct(String dbProduct) {
        this.dbProduct = dbProduct == null ? null : dbProduct.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitored_system.db_ip
     *
     * @return the value of monitored_system.db_ip
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public String getDbIp() {
        return dbIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitored_system.db_ip
     *
     * @param dbIp the value for monitored_system.db_ip
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public void setDbIp(String dbIp) {
        this.dbIp = dbIp == null ? null : dbIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitored_system.db_port
     *
     * @return the value of monitored_system.db_port
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public Short getDbPort() {
        return dbPort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitored_system.db_port
     *
     * @param dbPort the value for monitored_system.db_port
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public void setDbPort(Short dbPort) {
        this.dbPort = dbPort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitored_system.db_user
     *
     * @return the value of monitored_system.db_user
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitored_system.db_user
     *
     * @param dbUser the value for monitored_system.db_user
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public void setDbUser(String dbUser) {
        this.dbUser = dbUser == null ? null : dbUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitored_system.db_pwd
     *
     * @return the value of monitored_system.db_pwd
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public String getDbPwd() {
        return dbPwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitored_system.db_pwd
     *
     * @param dbPwd the value for monitored_system.db_pwd
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public void setDbPwd(String dbPwd) {
        this.dbPwd = dbPwd == null ? null : dbPwd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitored_system.schema
     *
     * @return the value of monitored_system.schema
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public String getSchema() {
        return schema;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitored_system.schema
     *
     * @param schema the value for monitored_system.schema
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public void setSchema(String schema) {
        this.schema = schema == null ? null : schema.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitored_system.create_time
     *
     * @return the value of monitored_system.create_time
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitored_system.create_time
     *
     * @param createTime the value for monitored_system.create_time
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitored_system.update_time
     *
     * @return the value of monitored_system.update_time
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitored_system.update_time
     *
     * @param updateTime the value for monitored_system.update_time
     *
     * @mbggenerated Wed Oct 14 19:13:54 CST 2015
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
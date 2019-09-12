package com.springboot.community.dto;

public class UserDTO {
    /**
     * GitHub用户名
     */
    private String name;

    /**
     * GitHub账号id
     */
    private Long id;

    /**
     * GitHub账号描述
     */
    private String bio;

    public UserDTO() {
    }

    public UserDTO(String name, Long id, String bio) {
        this.name = name;
        this.id = id;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }
}

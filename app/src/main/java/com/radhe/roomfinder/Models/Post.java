package com.radhe.roomfinder.Models;

public class Post {

  private String postImg,postId,postedBy;
  private int price,deposit;
  private String location,size,finshSt,bathSt,toilSt;
  private int park;
  private  String multipleImg;
  private long postedAt;

  public Post() {
  }

  public Post(long postedAt) {
    this.postedAt = postedAt;
  }

  public Post(String multipleImg) {
    this.multipleImg = multipleImg;
  }

  public Post(String postImg, String postId, String postedBy, int price, int deposit, String location, String size, String finshSt, String bathSt, String toilSt, int park) {
    this.postImg = postImg;
    this.postId = postId;
    this.postedBy = postedBy;
    this.price = price;
    this.deposit = deposit;
    this.location = location;
    this.size = size;
    this.finshSt = finshSt;
    this.bathSt = bathSt;
    this.toilSt = toilSt;
    this.park = park;
  }

  public long getPostedAt() {
    return postedAt;
  }

  public void setPostedAt(long postedAt) {
    this.postedAt = postedAt;
  }

  public String getMultipleImg() {
    return multipleImg;
  }

  public void setMultipleImg(String multipleImg) {
    this.multipleImg = multipleImg;
  }

  public String getPostImg() {
    return postImg;
  }

  public void setPostImg(String postImg) {
    this.postImg = postImg;
  }

  public String getPostId() {
    return postId;
  }

  public void setPostId(String postId) {
    this.postId = postId;
  }

  public String getPostedBy() {
    return postedBy;
  }

  public void setPostedBy(String postedBy) {
    this.postedBy = postedBy;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getDeposit() {
    return deposit;
  }

  public void setDeposit(int deposit) {
    this.deposit = deposit;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getFinshSt() {
    return finshSt;
  }

  public void setFinshSt(String finshSt) {
    this.finshSt = finshSt;
  }

  public String getBathSt() {
    return bathSt;
  }

  public void setBathSt(String bathSt) {
    this.bathSt = bathSt;
  }

  public String getToilSt() {
    return toilSt;
  }

  public void setToilSt(String toilSt) {
    this.toilSt = toilSt;
  }

  public int getPark() {
    return park;
  }

  public void setPark(int park) {
    this.park = park;
  }
}

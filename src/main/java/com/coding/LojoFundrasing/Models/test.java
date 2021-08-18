package com.coding.LojoFundrasing.Models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name="test")
public class test {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="test")
	private List<Contenttest> content;
    private String testcategory;
    private String VariantA;
    private String VariantB;
    private Integer goWinnerCountType1;
    private Integer clickWinnerCountType1;
    private Integer fullsendCountType1;
    private Integer goWinnerCountType2;
    private Integer clickWinnerCountType2;
    private Integer fullsendCountType2;
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="committees_id")
    private Committees committee;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Contenttest> getContent() {
		return content;
	}
	public void setContent(List<Contenttest> content) {
		this.content = content;
	}
	public String getTestcategory() {
		return testcategory;
	}
	public void setTestcategory(String testcategory) {
		this.testcategory = testcategory;
	}
	public String getVariantA() {
		return VariantA;
	}
	public void setVariantA(String variantA) {
		VariantA = variantA;
	}
	public String getVariantB() {
		return VariantB;
	}
	public void setVariantB(String variantB) {
		VariantB = variantB;
	}
	public Integer getGoWinnerCountType1() {
		return goWinnerCountType1;
	}
	public void setGoWinnerCountType1(Integer goWinnerCountType1) {
		this.goWinnerCountType1 = goWinnerCountType1;
	}
	public Integer getClickWinnerCountType11() {
		return clickWinnerCountType1;
	}
	public void setClickWinnerCountType1(Integer clickWinnerCountType1) {
		this.clickWinnerCountType1 = clickWinnerCountType1;
	}
	public Integer getFullsendCountType1() {
		return fullsendCountType1;
	}
	public void setFullsendCountType1(Integer fullsendCountType1) {
		this.fullsendCountType1 = fullsendCountType1;
	}
	
	public Integer getClickWinnerCountType1() {
		return clickWinnerCountType1;
	}
	public void setClickWinnerCountType11(Integer clickWinnerCountType1) {
		this.clickWinnerCountType1 = clickWinnerCountType1;
	}
	public Integer getGoWinnerCountType2() {
		return goWinnerCountType2;
	}
	public void setGoWinnerCountType2(Integer goWinnerCountType2) {
		this.goWinnerCountType2 = goWinnerCountType2;
	}
	public Integer getClickWinnerCountType2() {
		return clickWinnerCountType2;
	}
	public void setClickWinnerCountType2(Integer clickWinnerCountType2) {
		this.clickWinnerCountType2 = clickWinnerCountType2;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Committees getCommittee() {
		return committee;
	}
	public void setCommittee(Committees committee) {
		this.committee = committee;
	}
	
	
}

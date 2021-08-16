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
    private Integer goWinnerCount;
    private Integer clickWinnerCount;
    private Integer fullsendCount;
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
	public Integer getGoWinnerCount() {
		return goWinnerCount;
	}
	public void setGoWinnerCount(Integer goWinnerCount) {
		this.goWinnerCount = goWinnerCount;
	}
	public Integer getClickWinnerCount() {
		return clickWinnerCount;
	}
	public void setClickWinnerCount(Integer clickWinnerCount) {
		this.clickWinnerCount = clickWinnerCount;
	}
	public Integer getFullsendCount() {
		return fullsendCount;
	}
	public void setFullsendCount(Integer fullsendCount) {
		this.fullsendCount = fullsendCount;
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

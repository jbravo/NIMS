package com.viettel.nims.transmission.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DOCUMENT")
public class DocumentBO {
	private Long id;
	private Long documentId;
	private BigInteger documentType;
	private String fileName;
	private String path;
	private BigInteger attachFileType;

	private Date createTime;
	private Date provideEndDate;
	private Date provideStartDate;
	private String sgcnkdNumber;

	@Id
	@Basic
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Basic
	@Column(name = "DOCUMENT_ID")
	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	@Basic
	@Column(name = "FILE_NAME")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Basic
	@Column(name = "PATH")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Basic
	@Column(name = "TYPE")
	public BigInteger getAttachFileType() {
		return attachFileType;
	}

	public void setAttachFileType(BigInteger attachFileType) {
		this.attachFileType = attachFileType;
	}

	@Basic
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Basic
	@Column(name = "EXPIRED_DATE")
	public Date getProvideEndDate() {
		return provideEndDate;
	}

	public void setProvideEndDate(Date provideEndDate) {
		this.provideEndDate = provideEndDate;
	}

	@Basic
	@Column(name = "PROVIDE_DATE")
	public Date getProvideStartDate() {
		return provideStartDate;
	}

	public void setProvideStartDate(Date provideStartDate) {
		this.provideStartDate = provideStartDate;
	}

	@Basic
	@Column(name = "GCNKD")
	public String getSgcnkdNumber() {
		return sgcnkdNumber;
	}

	public void setSgcnkdNumber(String sgcnkdNumber) {
		this.sgcnkdNumber = sgcnkdNumber;
	}

	@Basic
	@Column(name = "DOCUMENT_TYPE")
	public BigInteger getDocumentType() {
		return documentType;
	}

	public void setDocumentType(BigInteger documentType) {
		this.documentType = documentType;
	}

}

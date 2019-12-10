package com.viettel.nims.transmission.model;

import com.viettel.nims.commons.util.PaginationDTO;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "CNT_LINE_SLEEVE_LINE")
public class WeldSleeveBO extends PaginationDTO {
	private PK pk;
	private BigDecimal attenuation;
	private Date createDate;
	private String createUser;
	private String note;
	private String basicInfo;

	@EmbeddedId
	@AttributeOverrides({
		  @AttributeOverride( name = "sourceCableId", column = @Column(name = "SOURCE_CABLE_ID")),
		  @AttributeOverride( name = "sourceLineNo", column = @Column(name = "SOURCE_LINE_NO")),
		  @AttributeOverride( name = "sleeveId", column = @Column(name = "SLEEVE_ID")),
		  @AttributeOverride( name = "destCableId", column = @Column(name = "DEST_CABLE_ID")),
		  @AttributeOverride( name = "destLineNo", column = @Column(name = "DEST_LINE_NO"))
		})
	public PK getPk() {
		return pk;
	}

	public void setPk(PK pk) {
		this.pk = pk;
	}

	@Column(name = "ATTENUATION")
	public BigDecimal getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(BigDecimal attenuation) {
		this.attenuation = attenuation;
	}

	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "CREATE_USER")
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "NOTE")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Transient
	public String getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(String basicInfo) {
		this.basicInfo = basicInfo;
	}
 
	@Embeddable
	public static class PK implements Serializable {
		private static final long serialVersionUID = 1L;
		@Column(name = "SOURCE_CABLE_ID")
		private Long sourceCableId;
		@Column(name = "SOURCE_LINE_NO")
		private Integer sourceLineNo;
		@Column(name = "SLEEVE_ID")
		private Long sleeveId;
		@Column(name = "DEST_CABLE_ID")
		private Long destCableId;
		@Column(name = "DEST_LINE_NO")
		private Integer destLineNo;

		// Get - set
		public Long getSourceCableId() {
			return sourceCableId;
		}

		public void setSourceCableId(Long sourceCableId) {
			this.sourceCableId = sourceCableId;
		}

		public Integer getSourceLineNo() {
			return sourceLineNo;
		}

		public void setSourceLineNo(Integer sourceLineNo) {
			this.sourceLineNo = sourceLineNo;
		}

		public Long getSleeveId() {
			return sleeveId;
		}

		public void setSleeveId(Long sleeveId) {
			this.sleeveId = sleeveId;
		}

		public Long getDestCableId() {
			return destCableId;
		}

		public void setDestCableId(Long destCableId) {
			this.destCableId = destCableId;
		}

		public Integer getDestLineNo() {
			return destLineNo;
		}

		public void setDestLineNo(Integer destLineNo) {
			this.destLineNo = destLineNo;
		}

		public PK() {
			super();
		}

		public PK(Long sourceCableId, Integer sourceLineNo, Long sleeveId, Long destCableId, Integer destLineNo) {
			super();
			this.sourceCableId = sourceCableId;
			this.sourceLineNo = sourceLineNo;
			this.sleeveId = sleeveId;
			this.destCableId = destCableId;
			this.destLineNo = destLineNo;
		}
	}

}

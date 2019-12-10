package com.viettel.nims.transmission.model;

import com.viettel.nims.transmission.utils.Constains;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "CNT_COUPLER_TO_COUPLER")
public class WeldingOdfToOdfBO implements Serializable {
	@Id
	@Column(name = "SOURCE_ODF_ID", nullable = false)
	private Long odfId;

	@Id
	@Column(name = "SOURCE_COUPLER_NO", nullable = false)
	private Long couplerNo;

	@Column(name = "DEST_ODF_ID", nullable = false)
	private Long destOdfId;

	@Column(name = "DEST_COUPLER_NO", nullable = false)
	private Long destCouplerNo;

	@Column(name = "ATTENUATION")
	private BigDecimal attenuation;

	@Column(name = "NOTE", length = 500)
	private String note;

	@Column(name = "CREATE_DATE")
	private Date createDate;

	@Column(name = "CREATE_USER", length = 100)
	private String createUser;

	@Transient
	private Integer odfConnectType = 2;

	public WeldingOdfToOdfBO(Long odfId, Long couplerNo, Long destOdfId, Long destCouplerNo, BigDecimal attenuation,
			String note, Date createDate, String createUser) {
		this.odfId = odfId;
		this.couplerNo = couplerNo;
		this.destOdfId = destOdfId;
		this.destCouplerNo = destCouplerNo;
		this.attenuation = attenuation;
		this.note = note;
		this.createDate = createDate;
		this.createUser = createUser;
	}

	public WeldingOdfToOdfBO() {
		super();
	}

}

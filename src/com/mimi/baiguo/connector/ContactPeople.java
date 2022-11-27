package com.mimi.baiguo.connector;
/**
 * 联系人
 */
import android.graphics.Bitmap;

public class ContactPeople {

	public String contactName; // 姓名
	public String contactPhone; // 手机
	public String contactHomePhone; // 宅电
	public String contactEmail; // Email
	public String contactAddress; // 地址
	public String contactOrganizCompany; // 联系人公司
	public String contactOrganizPost; // 联系人职位
	public String contactRemark; // 备注
	public String contactNickname; // 昵称
	public Bitmap photo; //

	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactHomePhone() {
		return contactHomePhone;
	}

	public void setContactHomePhone(String contactHomePhone) {
		this.contactHomePhone = contactHomePhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactOrganizCompany() {
		return contactOrganizCompany;
	}

	public void setContactOrganizCompany(String contactOrganizCompany) {
		this.contactOrganizCompany = contactOrganizCompany;
	}

	public String getContactOrganizPost() {
		return contactOrganizPost;
	}

	public void setContactOrganizPost(String contactOrganizPost) {
		this.contactOrganizPost = contactOrganizPost;
	}

	public String getContactRemark() {
		return contactRemark;
	}

	public void setContactRemark(String contactRemark) {
		this.contactRemark = contactRemark;
	}

	public String getContactNickname() {
		return contactNickname;
	}

	public void setContactNickname(String contactNickname) {
		this.contactNickname = contactNickname;
	}

}

package com.mimi.baiguo.connector;

/**
 * 封装获取联系人
 */
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

public class GetContacts {
	public static ArrayList<ContactPeople> getAllContacts(Context context) {

		ArrayList<ContactPeople> contacts = new ArrayList<ContactPeople>();

		Cursor cursor = context.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		while (cursor.moveToNext()) {

			// 新建一个联系人实例
			ContactPeople temp = new ContactPeople();
			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			String name = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			temp.contactName = name;

			// 获取联系人所有电话号
			Cursor phones = context.getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			while (phones.moveToNext()) {

				String phoneNumber = phones
						.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				temp.contactPhone = phoneNumber;
			}
			phones.close();
			// 获取联系人所有邮箱.
			// Cursor emails = context.getContentResolver().query(
			// ContactsContract.CommonDataKinds.Email.CONTENT_URI,
			// null,
			// ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = "
			// + contactId, null, null);
			//
			// while (emails.moveToNext()) {
			// String email = emails
			// .getString(emails
			// .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
			// temp.contactEmail = email;
			// }
			// emails.close();

			// 获取联系人头像
			// temp.photo = getContactPhoto(context, contactId,
			// R.drawable.icon);
			contacts.add(temp);
		}
		return contacts;
	}

	/**
	 * 获取手机联系人头像
	 * 
	 * @param c
	 * @param personId
	 * @param defaultIco
	 * @return
	 */
	private static Bitmap getContactPhoto(Context c, String personId,
			int defaultIco) {
		byte[] data = new byte[0];
		Uri u = Uri.parse("content://com.android.contacts/data");
		String where = "raw_contact_id = " + personId
				+ " AND mimetype ='vnd.android.cursor.item/photo'";
		Cursor cursor = c.getContentResolver()
				.query(u, null, where, null, null);
		if (cursor.moveToFirst()) {
			data = cursor.getBlob(cursor.getColumnIndex("data15"));
		}
		cursor.close();
		if (data == null || data.length == 0) {
			return BitmapFactory.decodeResource(c.getResources(), defaultIco);
		} else
			return BitmapFactory.decodeByteArray(data, 0, data.length);
	}
}

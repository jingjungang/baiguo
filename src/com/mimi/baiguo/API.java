package com.mimi.baiguo;

import android.graphics.Bitmap;

/*API接口文档*/
public class API {

	/** 用户ID **/
	public static String UserId = "";
	/** 判断是否上传头像 */
	public static Boolean uploading = false;
	/** 用户头像 */
	public static Bitmap bitmap;
	/** 用户头像地址 */
	public static String avatar_address = "";
	/** 游客账号 */
	public static String youke = "123";
	/** token值 */
	public static String token = null;
	/** 更新版本号 */
	public static int code = 120;
	public static String version = "1.2.0";
	/** 友盟ID */
	public static String YMuserid = null;
	/** 医生名字 */
	public static String doctorName = null;
	/** 登陆状态 */
	public static Boolean loginstate = false;
	/** 请求地址前缀 */
	public static final String HOST = "http://yd.baiyu.cn/api.php";
	/** 头像地址前缀 */
	public static final String AVATAR = "http://yd.baiyu.cn/";

	// 2016年5月20日 11:11:36 景俊钢 新接口
	/** 患者端用户注册 */
	public static final String USER_REGISTER_URL = HOST + "/user/newregister";
	/** 注册验证码 */
	public static final String USER_REGISTER_URL_YZM = HOST
			+ "/password/newsendcode";
	/** 用户验证登陆 */
	public static final String USER_LOGIN_URL = HOST + "/user/newlogin";
	/** 用户修改密码 */
	public static final String USER_MOD_PWD_URL = HOST + "/user/newupdatePass";
	/** 找回密码 */
	public static final String GET_BACK_PASSWOED = HOST
			+ "/password/newupdates";
	/** 找回密码-验证码 */
	public static final String GET_BACK_PASSWOED_YZM = HOST
			+ "/password/newindex";
	/** 患者基本信息查询 **/
	public static final String MYINFO = HOST + "/user/newindex";
	/** 患者基本信息修改 **/
	public static final String MYINFO_EDIT = HOST + "/patient/updatemyinfo";

	/** 关于我们 */
	public static final String ABOUT_US_URL = HOST + "/about";
	/** 患者账户信息 */
	public static final String USER_INFO_URL = HOST + "/user/newindex";
	/** 患者基本信息 */
	public static final String USER_INFOS_URL = HOST + "/patient/index";
	/** 患者端我的病例 */
	public static final String USER_CASE_URL = HOST + "/patient/mycase";
	/** 患者端医生列表 */
	public static final String USER_CONTACTS_URL = HOST + "/doctor/index";
	/** 患者端医生详情 */
	public static final String DOCTOR_INFO_URL = HOST
			+ "/rpatient/mydoctor/doctorshow";
	/** 资讯列表信息 */
	public static final String NEWS_INFO_URL = HOST + "/pubmed/reports";
	/** 资讯详情 */
	public static final String NEWS_INFOS_URL = HOST
			+ "/rpatient/zx_index/listsshow/";
	/** 上传头像 */
	public static final String USER_AVATAR_URL = HOST + "/user/newavatar";
	/** 知识头部菜单 */
	public static final String ZHISHI_TITLE_URL = HOST
			+ "/rpatient/zx_index/lists";
	/** 知识菜单列表 */
	public static final String ZHISHI_TITLELIST_URL = HOST
			+ "/rpatient/zx_index/listsAll/";
	/** 知识---热点 */
	public static final String KNOWLEDGEDETALIS_HOT = HOST
			+ "/rpatient/zx_index/newslist";
	/** 知识列表详情 */
	public static final String ZHISHI_XIANGQING_URL = HOST
			+ "/rpatient/zx_index/listsshow";
	/** 验证码 */
	public static final String YZM_URL = HOST + "/password/index";

	/** APK更新请求 */
	public static final String APKurl = HOST + "/version/index";
	/** APK下载地址 1正式版 2测试版 */
	public static final String APK_DOWN_URL = "http://yd.baiyu.cn/index.php/apkdown/patient/1";
	/** 关注列表 */
	public static final String ATTENTION_URL = HOST
			+ "/rpatient/mydoctor/mydoctorlist";
	/** 我的医生-互动 */
	public static String DOC_HELP_URL = HOST + "/rpatient/mydoctor/messagelist";
	/** 互动详情 **/
	public static final String HELP_DETAIL_URL = HOST
			+ "/rpatient/mydoctor/messageshow";
	/** 互动详情-添加提问 **/
	public static final String HELP_DETAIL_ADD_URL = HOST
			+ "/rpatient/mydoctor/sendreply";
	/** 添加网络咨询 */
	public static final String ADD_QUIZ_URL = HOST
			+ "/rpatient/mydoctor/sendmessage";
	/** 验证码-找回密码 */
	public static final String GETBACKPASSWORD_YZM_URL = HOST
			+ "/password/newindex";
	/** 手机联系人 */
	public static final String GET_PHONE_CONTACT = HOST
			+ "/rpatient/zx_index/inseruser";
	public static final String USER_AGENT = "Android;Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/4.4.2.2000 Chrome/30.0.1599.101 Safari/537.36";
	public static final String NO_MORE_DATA = "查询无数据";
	public static final String NO_DATA_EDIT = "你还没有编辑问题!";
	/** 排行榜 */
	public static final String RANK = HOST + "/rpatient/goods/steplist";
	/** 上传步数 */
	public static final String UPLOAD_STEP = HOST + "/rpatient/goods/addscore";
	/** 商品列表 */
	public static final String GIFT_LIST = HOST + "/rpatient/goods/index";
	/** 商品列表-详情 */
	public static final String GIFT_LIST_ITEM = HOST + "/rpatient/goods/show";
	/** 积分兑换商品 */
	public static final String EXCHANGE_GOODS = HOST
			+ "/rpatient/goods/changegoods";
	/** 积分兑换商品-记录 */
	public static final String EXCHANGE_GOODS_RECORDS = HOST
			+ "/rpatient/goods/changegoodslist";

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		API.token = token;
	}

	public static String getYMuserid() {
		return YMuserid;
	}

	public static void setYMuserid(String yMuserid) {
		YMuserid = yMuserid;
	}

	public static String getDoctorName() {
		return doctorName;
	}

	public static void setDoctorName(String doctorName) {
		API.doctorName = doctorName;
	}

	public static String getYouke() {
		return youke;
	}

	public static void setYouke(String youke) {
		API.youke = youke;
	}

}

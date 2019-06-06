package com.student.admin.easycalls.model;



import com.student.admin.easycalls.gettersetter.customernotpaidlist;
import com.student.admin.easycalls.gettersetter.discode;
import com.student.admin.easycalls.gettersetter.exelist;
import com.student.admin.easycalls.gettersetter.list;
import com.student.admin.easycalls.gettersetter.login;
import com.student.admin.easycalls.gettersetter.paidlist;
import com.student.admin.easycalls.gettersetter.tllist;
import com.student.admin.easycalls.gettersetter.visit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface api {

    @POST("webservice.php?Case=CommonLogin")
      Call<login> Login (
             @Query("user_name") String name, @Query(value = "user_password", encoded = true) String password1, @Query(value = "token", encoded = true) String token);


      @POST("webservice.php?Case=EmployeeList")
      Call<list>list();

      @POST("webservice.php?Case=Registration")
      Call<login> register(  @Query(value = "employee_name", encoded = true) String name,@Query(value = "employee_phone" , encoded = true)String phone,
                             @Query(value = "employee_pwd"  , encoded = true) String password,
                             @Query(value = "employee_position" , encoded = true)String h,@Query(value = "employee_type",encoded = true)String hh,@Query(value = "teaml_id",encoded = true)String teaml_id);

    @POST("webservice.php?Case=ExecutiveLocationSave")
       Call<login> exee (@Query("employee_id") String name,@Query("customer_name")String phone,
                          @Query("customer_address") String email11,
                         @Query("customer_accno")String h,   @Query("end_date")String end_date,   @Query("emp_type")String emp_type);

     //    end_date=mar20&emp_type=rest

     @POST("webservice.php?Case=ExecutiveLocationList")
         Call<exelist>list1(@Query("employee_id") String name);


    @POST("webservice.php?Case=CustomerExecutiveList1")
    Call<tllist>tllist(@Query("teaml_id") String name,@Query("exc_date") String name1);

   //http://www.dneers.com/easycalls/webservice.php?Case=CustomerExecutiveList&teaml_id=11

    @POST("webservice.php?Case=DispoCodeList")
    Call<discode>discodw1();

    @POST("webservice.php?Case=CurrentLocationSave")
    Call<exelist>addlatlong(      @Query("employee_id") String employee_id,@Query("current_lat") String current_lat,
                                  @Query("current_lang") String current_lang ,@Query("client_id") String client_id   );


    @POST("webservice.php?Case=ExecutiveDetailsSave")
    Call<exelist>addlatlong1(@Query("employee_id") String employee_id,
                               @Query("dispo_code") String dispo_code,
                               @Query("executive_summary") String executive_summary,
                               @Query("executive_other") String executive_other,
                               @Query("executive_location_lat") String executive_location_lat,
                               @Query("executive_location_long") String executive_location_long,
                               @Query("executive_transtype") String executive_transtype,
                               @Query("status_id") String status_id,
                               @Query("executive_time") String executive_time,
                               @Query("amount") String enter_amnt,
                               @Query("bank") String bank,
                               @Query("trans_id") String trans_id,
                               @Query("cheque_number") String cheque_number,
                               @Query("date") String date
                                );


    @POST("webservice.php?Case=CustomerExecutiveNotPaidlist")
    Call<customernotpaidlist>nopay(@Query("teaml_id") String name);

    @POST("webservice.php?Case=CustomerExecutivePaidlist")
    Call<paidlist>paylist(@Query("teaml_id") String name);

    @POST("webservice.php?Case=MyBaseList")
    Call<visit>baselist(@Query("teaml_id") String name);


//    http://www.dneers.com/easycalls/webservice.php?Case=CustomerExecutivePaidlist&teaml_id=64

//    http://www.dneers.com/easycalls/webservice.php?Case=MyBaseList&teaml_id=64
     //http://www.dneers.com/easycalls/webservice.php?Case=CustomerExecutiveNotPaidlist&teaml_id=64


    //http://www.dneers.com/easycalls/webservice.php?Case=ExecutiveDetailsSave
    // &employee_id=1&teamleader_id=3
    // &dispo_code=ANF
    // &executive_summary=ghsdsgah
    // &executive_other=ashjdfgasfgahg
    // &executive_location_lat=1.3920567
    // &executive_location_long=79.7147468
    // &executive_address=16/17%20Annamalai%20Nagar%204th%20Street
    // &status_id=11&executive_transtype=Online%20Transaction
    // &summary=ajhsgfajshg
    // &amount=100&trans_id=123541


    // http://www.dneers.com/easycalls/webservice.php?Case=ExecutiveDetailsSave&employee_id=1&
    // teamleader_id=3
    // &dispo_code=ANF&
    // executive_summary=ghsdsgah
    // &executive_other=ashjdfgasfgahg&
    // executive_location_lat=1.3920567&
    // executive_location_long=79.7147468&
    // executive_address=16/17%20Annamalai%20Nagar%204th%20Street&
    // status_id=11&
    // executive_transtype=Online%20Transaction&
    // summary=ajhsgfajshg&amount=100
    // &trans_id=123541&bank=asdf
    // &cheque_number=25&
    // date=mau23

    //http://www.dneers.com/easycalls/webservice.php?Case=ExecutiveDetailsSave&
    //employee_id=1&teamleader_id=3
    //&dispo_code=ANF&
    //executive_summary=ghsdsgah&
    //executive_other=ashjdfgasfgahg&
    //executive_location_lat=1.3920567&
    //executive_location_long=79.7147468&
    //executive_address=16/17%20Annamalai%20Nagar%204th%20Street&status_id=11&
    //executive_transtype=Cash&
    //summary=ajhsgfajshg
    //&amount=10
    //&trans_id=123541


    @POST("webservice.php?Case=WholedayTracking")
    Call<exelist>everttime(@Query("employee_id") String employee_id,@Query("emp_lat") String current_lat,
                            @Query("emp_lang") String current_lang );

    //http://www.dneers.com/easycalls/webservice.php?Case=WholedayTracking&employee_id=1&emp_lat=11.3920567&emp_lang=79.7147468
    //status_id=11&executive_time=02:99
    // http://www.dneers.com/easycalls/webservice.php?Case=ExecutiveDetailsSave
    // &employee_id=1
    // &dispo_code=ANF
    // &executive_summary=ghsdsgah
    // &executive_other=ashjdfgasfgahg
    // &executive_location_lat=1.3920567
    // &executive_location_long=79.7147468
    // &executive_transtype=cash

    // http://www.dneers.com/easycalls/webservice.php?Case=CurrentLocationSave
    //&employee_id=1
    //&current_lat=11.3920567
    //&current_lang=79.7147468

  //http://www.dneers.com/easycalls/webservice.php?Case=DispoCodeList
  //http://www.dneers.com/easycalls/webservice.php?Case=ExecutiveLocationList&employee_id=1
  //http://www.dneers.com/easycalls/webservice.php?Case=
  //ExecutiveLocationSave
  //&employee_id=1
  //&customer_name=george
  // &customer_phone=1236758976
  // &customer_accno=Asds
  //&customer_address=ahsgdfagfd


//http://www.dneers.com/easycalls/webservice.php?
//Case=LocationSave
//&employee_id=1
//&employee_lat=101.3
//&employee_lang=201.4
//&employee_address=street
//@POST("webservice.php?Case=DoctorLogin")
//Call<login> Login(
//@Query("doctor_phone") String name, @Query(value = "doctor_password", encoded = true) String password1, @Query(value = "token", encoded = true) String token);
//@POST("webservice.php?Case=TodayList")
//Call<pending> PendingList(@Query("doctor_id") String nam);
//@POST("webservice.php?Case=ApproveList1")
//Call<pending> PendingList1(@Query("doctor_id") String nam);


//   @POST("webservice.php?Case=BeforeConfirm")
//   Call<h> BeforeConfirm(@Query("patient_id") String nam);

//    @POST("webservice.php?Case=PatientPayment")
//    Call<pending> payment1(@Query("appoinment_id") String nam, @Query("amount") String nam1, @Query("paid_id") String namdfdsfsdf11);

//    @POST("webservice.php?Case=Daylist")
//    Call<pending> Daylist123(@Query("doctor_id") String nam, @Query("appoinment_date") String nam1);

//    @POST("webservice.php?Case=DateRangeList")
//    Call<pending> Daylist1234(@Query("doctor_id") String nam, @Query("start_date") String nam1, @Query("end_date") String nam11);

//    @POST("webservice.php?Case=BookingConfirm")
//    Call<pending> BookingConfirm(@Query("appoinment_id") String nam, @Query("status") String r);

//    @POST("webservice.php?Case=WeekListing")
//    Call<pending> WeekListing(@Query("doctor_id") String nam);

//    @POST("webservice.php?Case=ConfirmPendingList")
//    Call<pending>appoint(@Query("doctor_id") String nam);
//
//
//    @POST("webservice.php?Case=PatientConfirm")
//    Call<h> shedule1(@Query("patient_id") String nam);

//    @POST("webservice.php?Case=ReceptionistTodayList")
//    Call<h> rese(@Query("reception_id") String nam);
//    @POST("webservice.php?Case=ReceptionistWeekListing")
//    Call<h> week(@Query("reception_id") String nam);
//    @POST("webservice.php?Case=ForgotPassword")
//    Call<h> forgetpassword(@Query(value = "doctor_email", encoded = true) String doctor_email);

//    @POST("webservice.php?Case=DoctorList")
//    Call<doctorlist> getdoctor();

//    @POST("webservice.php?Case=PatientList")
//    Call<listinpes> getdoctor1();
//    Case=PatientRegistration&patient_name=dfgg&patient_phone=dfgdfg&patient_password=fdg
//    @POST("webservice.php?Case=PatientRegistration")
//    Call<common> register(@Query(value = "patient_name", encoded = true) String name, @Query("patient_phone") String phone, @Query(value = "patient_password", encoded = true) String password, @Query(value = "patient_email", encoded = true) String password1);
//    @GET("webservice.php?Case=ReceptionistDoctorAvailability")
//    Call<common> avaliable1(@Query("reception_id") String name, @Query("appoinment_date") String password, @Query(value = "appoinment_slot", encoded = true) String g, @Query("patient_phone") String f, @Query("patient_name") String id)

//    @GET("webservice.php?Case=DoctorAvailability")
//    Call<common> avaliable(@Query("doctor_id") String name, @Query("appoinment_date") String password, @Query(value = "appoinment_slot", encoded = true) String g, @Query("patient_id") String f, @Query("Id") String id);

//    DoctorMessage&message_text=asdfsaf&sender_id=12&receiver_id=23
//    @POST("webservice.php?Case=DoctorMessage")
//    Call<chat> chat1(@Query("message_text") String nam, @Query("sender_id") String chat_id, @Query("receiver_id") String chat_id1);

//    @POST("webservice.php?Case=LocationList")
//    Call<location> location(@Query("doctor_id") String nam);

//    @POST("webservice.php?Case=DoctorMessageList")
//    Call<chat> chat(@Query("login_id") String nam, @Query("chat_id") String chat_id);

//    @Multipart
//    @POST("webservice.php?Case=DoctorImageUpload")
//    Call<common> uploadimage(@Part MultipartBody.Part file, @Part("tem_name") RequestBody name);

//    Call<UploadObject> uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name)

//    uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name);

}



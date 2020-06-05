package com.student.admin.easycalls.gettersetter;

public class enddate {

    private Response Response;

    private End[] End;

    public Response getResponse ()
    {
        return Response;
    }

    public void setResponse (Response Response)
    {
        this.Response = Response;
    }

    public End[] getEnd ()
    {
        return End;
    }

    public void setEnd (End[] End)
    {
        this.End = End;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Response = "+Response+", End = "+End+"]";
    }

    public class Response
    {
        private String response_code;

        private String response_message;

        public String getResponse_code ()
        {
            return response_code;
        }

        public void setResponse_code (String response_code)
        {
            this.response_code = response_code;
        }

        public String getResponse_message ()
        {
            return response_message;
        }

        public void setResponse_message (String response_message)
        {
            this.response_message = response_message;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [response_code = "+response_code+", response_message = "+response_message+"]";
        }
    }



    public class End
    {
        private String end_date;

        private String customer_address;

        private String customer_phone;

        private String customer_accno;

        private String To_Be_Paid;

        private String BTC;

        private String employee_id;

        private String end_location_lat;

        private String To_be_collected;

        private String end_location_long;

        private String billzip_zip;

        private String customer_name;

        private String emp_type;

        private String status;

        public String getEnd_date ()
        {
            return end_date;
        }

        public void setEnd_date (String end_date)
        {
            this.end_date = end_date;
        }

        public String getCustomer_address ()
        {
            return customer_address;
        }

        public void setCustomer_address (String customer_address)
        {
            this.customer_address = customer_address;
        }

        public String getCustomer_phone ()
        {
            return customer_phone;
        }

        public void setCustomer_phone (String customer_phone)
        {
            this.customer_phone = customer_phone;
        }

        public String getCustomer_accno ()
        {
            return customer_accno;
        }

        public void setCustomer_accno (String customer_accno)
        {
            this.customer_accno = customer_accno;
        }

        public String getTo_Be_Paid ()
        {
            return To_Be_Paid;
        }

        public void setTo_Be_Paid (String To_Be_Paid)
        {
            this.To_Be_Paid = To_Be_Paid;
        }

        public String getBTC ()
        {
            return BTC;
        }

        public void setBTC (String BTC)
        {
            this.BTC = BTC;
        }

        public String getEmployee_id ()
        {
            return employee_id;
        }

        public void setEmployee_id (String employee_id)
        {
            this.employee_id = employee_id;
        }

        public String getEnd_location_lat ()
        {
            return end_location_lat;
        }

        public void setEnd_location_lat (String end_location_lat)
        {
            this.end_location_lat = end_location_lat;
        }

        public String getTo_be_collected ()
        {
            return To_be_collected;
        }

        public void setTo_be_collected (String To_be_collected)
        {
            this.To_be_collected = To_be_collected;
        }

        public String getEnd_location_long ()
        {
            return end_location_long;
        }

        public void setEnd_location_long (String end_location_long)
        {
            this.end_location_long = end_location_long;
        }

        public String getBillzip_zip ()
        {
            return billzip_zip;
        }

        public void setBillzip_zip (String billzip_zip)
        {
            this.billzip_zip = billzip_zip;
        }

        public String getCustomer_name ()
        {
            return customer_name;
        }

        public void setCustomer_name (String customer_name)
        {
            this.customer_name = customer_name;
        }

        public String getEmp_type ()
        {
            return emp_type;
        }

        public void setEmp_type (String emp_type)
        {
            this.emp_type = emp_type;
        }

        public String getStatus ()
        {
            return status;
        }

        public void setStatus (String status)
        {
            this.status = status;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [end_date = "+end_date+", customer_address = "+customer_address+", customer_phone = "+customer_phone+", customer_accno = "+customer_accno+", To_Be_Paid = "+To_Be_Paid+", BTC = "+BTC+", employee_id = "+employee_id+", end_location_lat = "+end_location_lat+", To_be_collected = "+To_be_collected+", end_location_long = "+end_location_long+", billzip_zip = "+billzip_zip+", customer_name = "+customer_name+", emp_type = "+emp_type+", status = "+status+"]";
        }
    }


}

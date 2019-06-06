package com.student.admin.easycalls.gettersetter;

public class visit {

    private Response Response;

    private List[] List;

    public Response getResponse ()
    {
        return Response;
    }

    public void setResponse (Response Response)
    {
        this.Response = Response;
    }

    public List[] getList ()
    {
        return List;
    }

    public void setList (List[] List)
    {
        this.List = List;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Response = "+Response+", List = "+List+"]";
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


    public class List
    {
        private  String executive_other;

        private  String executive_transtype;

        private  String executive_summary;

        private String customer_phone;

        private String  dispo_code;

        private String employee_name;

        private String Id;

        private String customer_name;

        private String customer_accno;

        private  String executive_time;

        public  String getExecutive_other ()
    {
        return executive_other;
    }

        public void setExecutive_other ( String executive_other)
        {
            this.executive_other = executive_other;
        }

        public String getExecutive_transtype ()
    {
        return executive_transtype;
    }

        public void setExecutive_transtype (String executive_transtype)
        {
            this.executive_transtype = executive_transtype;
        }

        public String getExecutive_summary ()
    {
        return executive_summary;
    }

        public void setExecutive_summary (String executive_summary)
        {
            this.executive_summary = executive_summary;
        }

        public String getCustomer_phone ()
        {
            return customer_phone;
        }

        public void setCustomer_phone (String customer_phone)
        {
            this.customer_phone = customer_phone;
        }

        public String getDispo_code ()
    {
        return dispo_code;
    }

        public void setDispo_code (String dispo_code)
        {
            this.dispo_code = dispo_code;
        }

        public String getEmployee_name ()
        {
            return employee_name;
        }

        public void setEmployee_name (String employee_name)
        {
            this.employee_name = employee_name;
        }

        public String getId ()
        {
            return Id;
        }

        public void setId (String Id)
        {
            this.Id = Id;
        }

        public String getCustomer_name ()
        {
            return customer_name;
        }

        public void setCustomer_name (String customer_name)
        {
            this.customer_name = customer_name;
        }

        public String getCustomer_accno ()
        {
            return customer_accno;
        }

        public void setCustomer_accno (String customer_accno)
        {
            this.customer_accno = customer_accno;
        }




    }



}

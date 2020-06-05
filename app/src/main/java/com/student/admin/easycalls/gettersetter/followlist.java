package com.student.admin.easycalls.gettersetter;

public class followlist {

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
            return "ClassPojo [response_code = "+response_code+", response_message ="+response_message+"]";
        }
    }



    public class End
    {
        private String executive_other;

        private String executive_transtype;

        private String dispo_code;

        private String executive_time;

        private String follow_up;

        private String BTC;

        private String team_leader_id;

        private String To_be_collect;

        private String executive_location_lat;

        private String employee_id;

        private String executive_summary;

        private String executive_location_long;

        private String Account_number;

        public String getExecutive_other ()
        {
            return executive_other;
        }

        public void setExecutive_other(String executive_other)
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

        public String getDispo_code ()
        {
            return dispo_code;
        }

        public void setDispo_code (String dispo_code)
        {
            this.dispo_code = dispo_code;
        }

        public String getExecutive_time ()
        {
            return executive_time;
        }

        public void setExecutive_time (String executive_time)
        {
            this.executive_time = executive_time;
        }

        public String getFollow_up ()
        {
            return follow_up;
        }

        public void setFollow_up (String follow_up)
        {
            this.follow_up = follow_up;
        }

        public String getBTC ()
        {
            return BTC;
        }

        public void setBTC (String BTC)
        {
            this.BTC = BTC;
        }

        public String getTeam_leader_id ()
        {
            return team_leader_id;
        }

        public void setTeam_leader_id (String team_leader_id)
        {
            this.team_leader_id = team_leader_id;
        }

        public String getTo_be_collect ()
        {
            return To_be_collect;
        }

        public void setTo_be_collect (String To_be_collect)
        {
            this.To_be_collect = To_be_collect;
        }

        public String getExecutive_location_lat ()
        {
            return executive_location_lat;
        }

        public void setExecutive_location_lat (String executive_location_lat)
        {
            this.executive_location_lat = executive_location_lat;
        }

        public String getEmployee_id ()
        {
            return employee_id;
        }

        public void setEmployee_id (String employee_id)
        {
            this.employee_id = employee_id;
        }

        public String getExecutive_summary ()
        {
            return executive_summary;
        }

        public void setExecutive_summary (String executive_summary)
        {
            this.executive_summary = executive_summary;
        }

        public String getExecutive_location_long ()
        {
            return executive_location_long;
        }

        public void setExecutive_location_long (String executive_location_long)
        {
            this.executive_location_long = executive_location_long;
        }

        public String getAccount_number ()
        {
            return Account_number;
        }

        public void setAccount_number (String Account_number)
        {
            this.Account_number = Account_number;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [executive_other = "+executive_other+", executive_transtype = "+executive_transtype+", dispo_code = "+dispo_code+", executive_time = "+executive_time+", follow_up = "+follow_up+", BTC = "+BTC+", team_leader_id = "+team_leader_id+", To_be_collect = "+To_be_collect+", executive_location_lat = "+executive_location_lat+", employee_id = "+employee_id+", executive_summary = "+executive_summary+", executive_location_long = "+executive_location_long+", Account_number = "+Account_number+"]";
        }
    }

}

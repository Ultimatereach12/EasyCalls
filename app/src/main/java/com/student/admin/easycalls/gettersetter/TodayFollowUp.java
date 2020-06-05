package com.student.admin.easycalls.gettersetter;

public class TodayFollowUp {

    private Response Response;

    private String Today;

    private String End;

    public Response getResponse ()
    {
        return Response;
    }

    public void setResponse (Response Response)
    {
        this.Response = Response;
    }

    public String getToday ()
    {
        return Today;
    }

    public void setToday (String Today)
    {
        this.Today = Today;
    }

    public String getEnd ()
    {
        return End;
    }

    public void setEnd (String End)
    {
        this.End = End;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Response = "+Response+", Today = "+Today+", End = "+End+"]";
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
}

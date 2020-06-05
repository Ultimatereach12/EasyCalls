package com.student.admin.easycalls.gettersetter;

public class totalreport {
    private Response Response;

    private TotalReport[] TotalReport;

    public Response getResponse ()
    {
        return Response;
    }

    public void setResponse (Response Response)
    {
        this.Response = Response;
    }

    public TotalReport[] getTotalReport ()
    {
        return TotalReport;
    }

    public void setTotalReport (TotalReport[] TotalReport)
    {
        this.TotalReport = TotalReport;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Response = "+Response+", TotalReport = "+TotalReport+"]";
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


    public class TotalReport
    {
        private String BTC;

        private String Missed_amount;

        private String To_be_collected;

        private String Missed;

        private String Actual;

        private String Collected;

        public String getBTC ()
        {
            return BTC;
        }

        public void setBTC (String BTC)
        {
            this.BTC = BTC;
        }

        public String getMissed_amount ()
        {
            return Missed_amount;
        }

        public void setMissed_amount (String Missed_amount)
        {
            this.Missed_amount = Missed_amount;
        }

        public String getTo_be_collected ()
        {
            return To_be_collected;
        }

        public void setTo_be_collected (String To_be_collected)
        {
            this.To_be_collected = To_be_collected;
        }

        public String getMissed ()
        {
            return Missed;
        }

        public void setMissed (String Missed)
        {
            this.Missed = Missed;
        }

        public String getActual ()
        {
            return Actual;
        }

        public void setActual (String Actual)
        {
            this.Actual = Actual;
        }

        public String getCollected ()
        {
            return Collected;
        }

        public void setCollected (String Collected)
        {
            this.Collected = Collected;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [BTC = "+BTC+", Missed_amount = "+Missed_amount+", To_be_collected = "+To_be_collected+", Missed = "+Missed+", Actual = "+Actual+", Collected = "+Collected+"]";
        }
    }



}

package io.promagent.agent.core.config;

public interface LogConstants {

    String request = "req";
    String reg_url = "url";
    String reg_header = "header";
    String reg_params = "params";
    String reg_status = "status";

    String method = "met";
    String met_args = "args";
    String met_ret = "ret";
    String met_sign = "sign";
    String met_exec = "exec";
    String met_thrown = "thrown";

    String basic = "basic";
    String basic_sn = "sn";
    String basic_ip = "ip";
    String basic_type = "type";
    String basic_grade = "grade";

//    String mdc = "mdc";
    String mdc_logId = System.getProperty("agent.mdcLogId");
//    String mdc_appName = "appName";
//    String mdc_appEvn = "appEvn";

//    String mdc_aop_msg= "aopMsg";
//    String mdc_type = "type";

    String null_string = "NULL";
    String skip = "skip";

    String PreviousSignature = "PreviousSignature";
    String LogPrinted = "LogPrinted";
    String RequestTimeStamp = "RequestTimeStamp";

    String HandlerInterceptorSign = "HandlerInterceptor.preHandle";
    String FilterSign = "Filter.doFilter";
    String HttpServletSign = "HttpServlet.service";
    String LoggerErrSign = "Logger.error";
    String LoggerInfoSign = "Logger.info";
}

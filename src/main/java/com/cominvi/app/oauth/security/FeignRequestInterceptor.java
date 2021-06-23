package com.cominvi.app.oauth.security;



//@Component
public class FeignRequestInterceptor /*implements RequestInterceptor*/ {

	/*private Logger log = LoggerFactory.getLogger(FeignRequestInterceptor.class);
	private static final String AUTORIZATION = "Authorization";
	
	@Override
	public void apply(RequestTemplate requestTemplate) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        if (request == null) {
            return;
        }
        String token = request.getHeader(AUTORIZATION);
        log.info(token);
        if (token == null) {
            return;
        }
        requestTemplate.header(AUTORIZATION, token);
		
	}*/

}

package com.saar.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.saar.blog.security.CustomUserDetailService;
import com.saar.blog.security.JwtAuthenticationEntryPoint;
import com.saar.blog.security.JwtAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Override //HttpSecurity is used to define security-related configurations like authentication, authorization, and CSRF protection
	protected void configure(HttpSecurity http) throws Exception{
		/*
		// This is for Basic authentication
		http.csrf()//CSRF (Cross-Site Request Forgery) protection is being disabled here.
		.disable()  //Disabling CSRF can be useful for APIs or non-browser-based clients, but it should be done cautiously because it removes a layer of security.
		.authorizeHttpRequests() // This method is used to define which HTTP requests need to be authorized (secured).
		.anyRequest()
		.authenticated() // This means that any HTTP request to the application (like /home, /login, etc.) will require the user to be authenticated (logged in).
		.and() //It's just a way to combine different configurations in a readable manner.
		.httpBasic(); //This enables HTTP Basic Authentication. 
		
		*/
		
		// for JWT  authentication
		http.csrf()
		.disable() 
		.authorizeHttpRequests() 
		.antMatchers("/api/v1/auth/**").permitAll() // sirf diye huve api ke key se hi delete kiya ja sakta hai 
		.antMatchers(HttpMethod.GET).permitAll()// get ki sari api bina login ke bhi ho jayegi 
		.anyRequest()
		.authenticated() 
		.and()
		.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	}
	
	/*
	  protected void configure(HttpSecurity http) throws Exception: यह मेथड एप्लिकेशन की सुरक्षा सेट करता है।

http.csrf().disable(): CSRF सुरक्षा को बंद कर दिया गया है। इसका मतलब है कि हम CSRF हमलों से बचने के लिए चेक नहीं करेंगे।

http.authorizeHttpRequests(): यह सेट करता है कि कौन-कौन सी रिक्वेस्ट्स को सुरक्षित करना है।

anyRequest().authenticated(): इसका मतलब है कि एप्लिकेशन में कोई भी पेज या API खोलने के लिए यूज़र को लॉगिन करना जरूरी होगा।

.and(): यह दूसरे सेटिंग्स को जोड़ने के लिए है।

httpBasic(): यह बेसिक ऑथेंटिकेशन को ऑन करता है, यानी यूज़र को username और password से लॉगिन करना होगा।*/

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
		super.configure(auth);
	}
	/*
	  @Override: यह बताता है कि हम एक पहले से मौजूद मेथड को बदल रहे हैं।

protected void configure(AuthenticationManagerBuilder auth) throws Exception: यह मेथड ऑथेंटिकेशन से जुड़ी सेटिंग्स को कॉन्फ़िगर करता है। AuthenticationManagerBuilder का इस्तेमाल यूज़र को प्रमाणित करने के लिए किया जाता है।

auth.userDetailsService(this.customUserDetailService): इसका मतलब है कि यूज़र के डिटेल्स को customUserDetailService से लिया जाएगा। यहां customUserDetailService एक कस्टम सेवा है, जो यूज़र की जानकारी को लाती है।

passwordEncoder(passwordEncoder()): यह पासवर्ड को एन्कोड करने के लिए passwordEncoder() का उपयोग करता है। इसका मतलब है कि यूज़र का पासवर्ड सुरक्षित रूप से स्टोर किया जाएगा।

super.configure(auth): यह पेरेंट क्लास की configure मेथड को कॉल करता है, जिससे बाकी की डिफ़ॉल्ट सुरक्षा सेटिंग्स लागू होती हैं।*/
    
    @Bean
    public PasswordEncoder passwordEncoder()
    {	
    	return new BCryptPasswordEncoder();
    }
    /*
     * @Bean: यह annotation बताता है कि यह मेथड एक Spring Bean बनाएगा। Spring Container इस Bean को मैनेज करेगा और इसे दूसरे हिस्सों में इस्तेमाल किया जा सकेगा।

public PasswordEncoder passwordEncoder(): यह मेथड एक PasswordEncoder (पासवर्ड एन्कोडर) ऑब्जेक्ट लौटाता है, जो पासवर्ड को सुरक्षित तरीके से एन्कोड (encrypt) करने के लिए काम आता है।

return new BCryptPasswordEncoder();: यह BCryptPasswordEncoder क्लास का एक नया ऑब्जेक्ट बनाता है। BCryptPasswordEncoder एक एन्कोडर है जो पासवर्ड को सुरक्षित तरीके से एन्कोड करता है। इसे पासवर्ड को स्टोर करने और उसकी तुलना करने के लिए इस्तेमाल किया जाता है।*/

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
    	return super.authenticationManagerBean();
    }
    

}
    
    


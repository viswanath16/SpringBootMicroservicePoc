package com.springsecurityjwt;

import com.springsecurityjwt.filters.JwtRequestFilter;
import com.springsecurityjwt.models.AuthenticationRequest;
import com.springsecurityjwt.models.AuthenticationResponse;
import com.springsecurityjwt.util.JwtUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class EmployeeMicroserviceJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeMicroserviceJwtApplication.class, args);
	}

}

@RestController
class HelloWorldController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}


@RestController
class EmployeeServiceController {
 
	private static Map<String, List<Employee>> mapEmp = new HashMap<String, List<Employee>>();
 	static {
    	mapEmp = new HashMap<String, List<Employee>>();
 
        List<Employee> lst = new ArrayList<Employee>();
        Employee emp = new Employee("Viswa", "Edinburgh");
        lst.add(emp);
        emp = new Employee("Vijay", "Phoenix");
        lst.add(emp);
 
        mapEmp.put("Syntel", lst);
 
        lst = new ArrayList<Employee>();
        emp = new Employee("Rakesh", "Bangalore");
        lst.add(emp);
        emp = new Employee("Vishnu", "Hyderabad");
        lst.add(emp);
 
        mapEmp.put("Atos", lst);
    }
    
    
    @RequestMapping(value = "/getEmployeeDetailsForCompany/{companyname}", method = RequestMethod.GET)
    public List<Employee> getEmployee(@PathVariable String companyname) {
        System.out.println("Getting Employee details for " + companyname);
 
        List<Employee> employeeList = mapEmp.get(companyname);
        if (employeeList == null) {
        	employeeList = new ArrayList<Employee>();
            Employee emp = new Employee("Not Found", "N/A");
            employeeList.add(emp);
        }
        return employeeList;
    }

}

@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService myUserDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				.authorizeRequests().antMatchers("/authenticate").permitAll().
						anyRequest().authenticated().and().
						exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}

}
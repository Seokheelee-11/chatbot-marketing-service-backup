//package com.shinhancard.chatbot.service;
//
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import nextstep.UnAuthenticationException;
//import nextstep.domain.UserRepository;
//import support.test.BaseTest;
//
//@RunWith(MockitoJUnitRunner.class)
//public class UserServiceTest extends BaseTest {
//	@Mock
//	private UserRepository userRepository;
//	@InjectMocks
//	private UserService userService;
//
//	@Test
//	public void login_success() throws Exception {
//		User user = new User("sanjigi", "password", "name", "javajigi@slipp.net");
//		when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(user));
//		User loginUser = userService.login(user.getUserId(), user.getPassword());
//		softly.assertThat(loginUser).isEqualTo(user);
//	}
//
//	@Test(expected = UnAuthenticationException.class)
//	public void login_failed_when_user_not_found() throws Exception {
//		when(userRepository.findByUserId("sanjigi")).thenReturn(Optional.empty());
//		userService.login("sanjigi", "password");
//	}
//
//	@Test(expected = UnAuthenticationException.class)
//	public void login_failed_when_mismatch_password() throws Exception {
//		User user = new User("sanjigi", "password", "name", "javajigi@slipp.net");
//		when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(user));
//		userService.login(user.getUserId(), user.getPassword() + "2");
//	}
//}
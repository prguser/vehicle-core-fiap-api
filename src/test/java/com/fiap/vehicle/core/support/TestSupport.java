package com.fiap.vehicle.core.support;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fiap.vehicle.core.BeanUtil;
import com.fiap.vehicle.core.FiapIdGenerator;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.fiap.vehicle.core.BeanUtil.getBean;
import static java.time.Clock.fixed;
import static java.time.Instant.ofEpochMilli;
import static java.time.ZoneId.systemDefault;

@Getter
public abstract class TestSupport {

	private static final long DATE_FEB_28_02_2023_15_44_29 = 1677599069000L;

	private MockedStatic<FiapIdGenerator> idGeneratorMock;

	private MockedStatic<BeanUtil> beanUtilMock;

	private Map<Class<?>, MockedStatic<?>> mockedStatics = new HashMap<>();

	@Getter
	private final Clock clock = fixed(ofEpochMilli(DATE_FEB_28_02_2023_15_44_29), systemDefault());

	public abstract void init();

	@BeforeAll
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("com.fiap");
	}

	@BeforeEach
	public void setUpTest() {
		MockitoAnnotations.openMocks(this);
		idGeneratorMock = getMockedStatic(FiapIdGenerator.class);
		beanUtilMock = getMockedStatic(BeanUtil.class);
		idGeneratorMock.when(FiapIdGenerator::generateId).thenReturn(UUID.randomUUID().toString());
		beanUtilMock.when(() -> getBean(Clock.class)).thenReturn(clock);
		this.init();
	}

	@AfterEach
	public void teardown() {
		mockedStatics.values().stream().filter(item -> !item.isClosed()).forEach(MockedStatic::close);
		mockedStatics.clear();
	}

	public <T> MockedStatic<T> getMockedStatic(Class<T> classToMock) {
		return (MockedStatic<T>) mockedStatics.computeIfAbsent(classToMock, Mockito::mockStatic);
	}

	public InOrder inOrder(Object... mocks) {
		return Mockito.inOrder(mocks);
	}

	public <T> OngoingStubbing<T> when(T methodCall) {
		return Mockito.when(methodCall);
	}

	public <T> T verify(T mock) {
		return Mockito.verify(mock);
	}

	public <T> T verify(T mock, int wantedNumberOfInvocations) {
		return Mockito.verify(mock, Mockito.times(wantedNumberOfInvocations));
	}

	public <T> boolean testPrivateConstructor(Class<T> clazz) {

		boolean hasPrivateConstructor = false;
		Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
		if (declaredConstructors != null && declaredConstructors[0] != null) {
			hasPrivateConstructor = Modifier.isPrivate(declaredConstructors[0].getModifiers());
		}

		return hasPrivateConstructor;
	}

}

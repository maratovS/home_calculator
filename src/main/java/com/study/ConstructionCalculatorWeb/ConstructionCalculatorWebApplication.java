package com.study.ConstructionCalculatorWeb;

import com.study.ConstructionCalculatorWeb.entity.*;
import com.study.ConstructionCalculatorWeb.repo.*;
import com.study.ConstructionCalculatorWeb.service.FrameService;
import com.study.ConstructionCalculatorWeb.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class ConstructionCalculatorWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConstructionCalculatorWebApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommandLineRunner run(GroupOfUsersRepository groupOfUsersRepository,
								 UserStatusRepository userStatusRepository,
								 UserRepository userRepository,
								 UserService userService,
								 MaterialRepository materialRepository,
								 MaterialCharacteristicsRepository materialCharacteristicsRepository,
								 UnitRepository unitRepository,
								 PriceListRepository priceListRepository,
								 CustomerRepository customerRepository,
								 CalculationRepository calculationRepository,
								 ResultsRepository resultsRepository,
								 ApertureRepository apertureRepository,
								 StatusRepository statusRepository,
								 FrameRepository frameRepository,
								 AperturesInFramesRepository aperturesInFramesRepository,
								 FrameService frameService
	){
		return (String[] args) -> {
			if (groupOfUsersRepository.findAll().isEmpty()) {
				groupOfUsersRepository.save(new GroupOfUsers(null, "ROLE_USER"));
				groupOfUsersRepository.save(new GroupOfUsers(null, "ROLE_MANAGER"));
				groupOfUsersRepository.save(new GroupOfUsers(null, "ROLE_ADMIN"));
			}
			if (userStatusRepository.findAll().isEmpty()){
				userStatusRepository.save(new UserStatus(null, "Числится в штате"));
				userStatusRepository.save(new UserStatus(null, "Уволен"));
			}
			if (userRepository.findAll().isEmpty()) {
				List<GroupOfUsers> groups = groupOfUsersRepository.findAll();
				UserStatus status = userStatusRepository.findByStatusName("Числится в штате");
				userService.addUser(new User(
						null,
						status,
						groups,
						"Overlordov",
						"Overlord",
						"Overlordovich",
						9093717141L,
						"ololo@gmail.com",
						"overlord",
						"123456"
				));
				userService.addUser(new User(
						null,
						status,
						groups,
						"Толч",
						"Алексей",
						"Overlordovich",
						9093987654L,
						"ololo@gmail.com",
						"alexey99",
						"123456"
				));
				userService.addUser(new User(
						null,
						status,
						groups,
						"Малинин",
						"Игорь",
						"Игоревич",
						9093745678L,
						"ololo@gmail.com",
						"barbie9925",
						"123456"
				));
			}
			if (unitRepository.findAll().isEmpty()){
				unitRepository.save(new Unit(null, "м2"));
				unitRepository.save(new Unit(null, "м3"));
			}
			if (priceListRepository.findAll().isEmpty()){
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						12000,
						12000 * 1.2
				));
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						256,
						256 * 1.2
				));
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						288,
						288 * 1.2
				));
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						384,
						384 * 1.2
				));
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						480,
						480 * 1.2
				));
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						3000,
						3000 * 1.2
				));
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						3500,
						3500 * 1.2
				));
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						2800,
						2800 * 1.2
				));
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						3200,
						3200 * 1.2
				));
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						33,
						33 * 1.2
				));
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						20,
						20 * 1.2
				));
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						21,
						21 * 1.2
				));
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						11,
						11 * 1.2
				));
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						57,
						57 * 1.2
				));
				priceListRepository.save(new PriceList(
						null,
						new Date(System.currentTimeMillis()),
						53,
						53 * 1.2
				));
			}
			if (materialCharacteristicsRepository.findAll().isEmpty()){
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Доска 50*100*3000",
						0.05,
						0.1,
						3,
						0.05 * 0.1 * 3,
						priceListRepository.findByPurchasePrice(12000),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Доска 50*150*3000",
						0.05,
						0.15,
						3,
						0.05 * 0.15 * 3,
						priceListRepository.findByPurchasePrice(12000),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Доска 50*200*3000",
						0.05,
						0.2,
						3,
						0.05 * 0.2 * 3,
						priceListRepository.findByPurchasePrice(12000),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Доска 50*250*3000",
						0.05,
						0.25,
						3,
						0.05 * 0.25 * 3,
						priceListRepository.findByPurchasePrice(12000),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Доска 50*300*3000",
						0.05,
						0.3,
						3,
						0.05 * 0.3 * 3,
						priceListRepository.findByPurchasePrice(12000),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Доска 50*100*6000",
						0.05,
						0.1,
						6,
						0.05 * 0.1 * 6,
						priceListRepository.findByPurchasePrice(12000),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Доска 50*150*6000",
						0.05,
						0.15,
						6,
						0.05 * 0.15 * 6,
						priceListRepository.findByPurchasePrice(12000),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Доска 50*200*6000",
						0.05,
						0.2,
						6,
						0.05 * 0.2 * 6,
						priceListRepository.findByPurchasePrice(12000),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Доска 50*250*6000",
						0.05,
						0.25,
						6,
						0.05 * 0.25 * 6,
						priceListRepository.findByPurchasePrice(12000),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Доска 50*300*6000",
						0.05,
						0.3,
						6,
						0.05 * 0.3 * 6,
						priceListRepository.findByPurchasePrice(12000),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"OSB 9 мм",
						1,
						0.09,
						1,
						0.09,
						priceListRepository.findByPurchasePrice(256),
						unitRepository.findByName("м2")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"OSB 10 мм",
						1,
						0.1,
						1,
						0.1,
						priceListRepository.findByPurchasePrice(288),
						unitRepository.findByName("м2")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"OSB 15 мм",
						1,
						0.15,
						1,
						0.15,
						priceListRepository.findByPurchasePrice(384),
						unitRepository.findByName("м2")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"OSB 18 мм",
						1,
						0.18,
						1,
						0.18,
						priceListRepository.findByPurchasePrice(480),
						unitRepository.findByName("м2")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Кнауф ТеплоКнауф 100 мм",
						1,
						0.1,
						1,
						0.1,
						priceListRepository.findByPurchasePrice(3000),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Технониколь 100 мм",
						1,
						0.1,
						1,
						0.1,
						priceListRepository.findByPurchasePrice(3500),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Эковер 100 мм",
						1,
						0.1,
						1,
						0.1,
						priceListRepository.findByPurchasePrice(2800),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Эковер 150 мм",
						1,
						0.15,
						1,
						0.15,
						priceListRepository.findByPurchasePrice(2800),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Эковер 200 мм",
						1,
						0.2,
						1,
						0.2,
						priceListRepository.findByPurchasePrice(2800),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Фасад 200 мм",
						1,
						0.2,
						1,
						0.2,
						priceListRepository.findByPurchasePrice(3200),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Эковер 250 мм",
						1,
						0.25,
						1,
						0.25,
						priceListRepository.findByPurchasePrice(2800),
						unitRepository.findByName("м3")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Ондутис",
						1,
						1,
						1,
						1,
						priceListRepository.findByPurchasePrice(33),
						unitRepository.findByName("м2")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Пароизоляция Axton (b)",
						1,
						1,
						1,
						1,
						priceListRepository.findByPurchasePrice(20),
						unitRepository.findByName("м2")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Пароизоляционная пленка Ютафол Н 96 Сильвер",
						1,
						1,
						1,
						1,
						priceListRepository.findByPurchasePrice(21),
						unitRepository.findByName("м2")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Пароизоляция В",
						1,
						1,
						1,
						1,
						priceListRepository.findByPurchasePrice(11),
						unitRepository.findByName("м2")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Ветро-влагозащитная мембрана Brane А",
						1,
						1,
						1,
						1,
						priceListRepository.findByPurchasePrice(57),
						unitRepository.findByName("м2")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Паропроницаемая ветро-влагозащита A Optima",
						1,
						1,
						1,
						1,
						priceListRepository.findByPurchasePrice(21),
						unitRepository.findByName("м2")
				));
				materialCharacteristicsRepository.save(new MaterialCharacteristics(
						null,
						"Гидро-ветрозащита Тип А",
						1,
						1,
						1,
						1,
						priceListRepository.findByPurchasePrice(53),
						unitRepository.findByName("м2")
				));
			}
			if (materialRepository.findAll().isEmpty()){
				materialRepository.save(new Material(
						null,
						"Доска 50*100*3000",
						"Пиломатериал",
						materialCharacteristicsRepository.findByName("Доска 50*100*3000")
				));
				materialRepository.save(new Material(
						null,
						"Доска 50*150*3000",
						"Пиломатериал",
						materialCharacteristicsRepository.findByName("Доска 50*150*3000")
				));
				materialRepository.save(new Material(
						null,
						"Доска 50*200*3000",
						"Пиломатериал",
						materialCharacteristicsRepository.findByName("Доска 50*200*3000")
				));
				materialRepository.save(new Material(
						null,
						"Доска 50*250*3000",
						"Пиломатериал",
						materialCharacteristicsRepository.findByName("Доска 50*250*3000")
				));
				materialRepository.save(new Material(
						null,
						"Доска 50*300*3000",
						"Пиломатериал",
						materialCharacteristicsRepository.findByName("Доска 50*300*3000")
				));
				materialRepository.save(new Material(
						null,
						"Доска 50*100*6000",
						"Пиломатериал",
						materialCharacteristicsRepository.findByName("Доска 50*100*6000")
				));
				materialRepository.save(new Material(
						null,
						"Доска 50*150*6000",
						"Пиломатериал",
						materialCharacteristicsRepository.findByName("Доска 50*150*6000")
				));
				materialRepository.save(new Material(
						null,
						"Доска 50*200*6000",
						"Пиломатериал",
						materialCharacteristicsRepository.findByName("Доска 50*200*6000")
				));
				materialRepository.save(new Material(
						null,
						"Доска 50*250*6000",
						"Пиломатериал",
						materialCharacteristicsRepository.findByName("Доска 50*250*6000")
				));
				materialRepository.save(new Material(
						null,
						"Доска 50*300*6000",
						"Пиломатериал",
						materialCharacteristicsRepository.findByName("Доска 50*300*6000")
				));
				materialRepository.save(new Material(
						null,
						"OSB 9 мм",
						"OSB",
						materialCharacteristicsRepository.findByName("OSB 9 мм")
				));
				materialRepository.save(new Material(
						null,
						"OSB 10 мм",
						"OSB",
						materialCharacteristicsRepository.findByName("OSB 10 мм")
				));
				materialRepository.save(new Material(
						null,
						"OSB 15 мм",
						"OSB",
						materialCharacteristicsRepository.findByName("OSB 15 мм")
				));
				materialRepository.save(new Material(
						null,
						"OSB 18 мм",
						"OSB",
						materialCharacteristicsRepository.findByName("OSB 18 мм")
				));
				materialRepository.save(new Material(
						null,
						"Кнауф ТеплоКнауф 100 мм",
						"Утеплитель",
						materialCharacteristicsRepository.findByName("Кнауф ТеплоКнауф 100 мм")
				));
				materialRepository.save(new Material(
						null,
						"Технониколь 100 мм",
						"Утеплитель",
						materialCharacteristicsRepository.findByName("Технониколь 100 мм")
				));
				materialRepository.save(new Material(
						null,
						"Эковер 100 мм",
						"Утеплитель",
						materialCharacteristicsRepository.findByName("Эковер 100 мм")
				));
				materialRepository.save(new Material(
						null,
						"Эковер 150 мм",
						"Утеплитель",
						materialCharacteristicsRepository.findByName("Эковер 150 мм")
				));
				materialRepository.save(new Material(
						null,
						"Эковер 200 мм",
						"Утеплитель",
						materialCharacteristicsRepository.findByName("Эковер 200 мм")
				));
				materialRepository.save(new Material(
						null,
						"Фасад 200 мм",
						"Утеплитель",
						materialCharacteristicsRepository.findByName("Фасад 200 мм")
				));
				materialRepository.save(new Material(
						null,
						"Эковер 250 мм",
						"Утеплитель",
						materialCharacteristicsRepository.findByName("Эковер 250 мм")
				));
				materialRepository.save(new Material(
						null,
						"Ондутис",
						"Пароизоляция",
						materialCharacteristicsRepository.findByName("Ондутис")
				));
				materialRepository.save(new Material(
						null,
						"Пароизоляция Axton (b)",
						"Пароизоляция",
						materialCharacteristicsRepository.findByName("Пароизоляция Axton (b)")
				));
				materialRepository.save(new Material(
						null,
						"Пароизоляционная пленка Ютафол Н 96 Сильвер",
						"Пароизоляция",
						materialCharacteristicsRepository.findByName("Пароизоляционная пленка Ютафол Н 96 Сильвер")
				));
				materialRepository.save(new Material(
						null,
						"Пароизоляция В",
						"Пароизоляция",
						materialCharacteristicsRepository.findByName("Пароизоляция В")
				));
				materialRepository.save(new Material(
						null,
						"Ветро-влагозащитная мембрана Brane А",
						"Ветрозащита",
						materialCharacteristicsRepository.findByName("Ветро-влагозащитная мембрана Brane А")
				));
				materialRepository.save(new Material(
						null,
						"Паропроницаемая ветро-влагозащита A Optima",
						"Ветрозащита",
						materialCharacteristicsRepository.findByName("Паропроницаемая ветро-влагозащита A Optima")
				));
				materialRepository.save(new Material(
						null,
						"Гидро-ветрозащита Тип А",
						"Ветрозащита",
						materialCharacteristicsRepository.findByName("Гидро-ветрозащита Тип А")
				));
			}
			if (statusRepository.findAll().isEmpty()){
				statusRepository.save(new Status(
						null,
						"Актуален"
				));
				statusRepository.save(new Status(
						null,
						"Заключен договор"
				));
				statusRepository.save(new Status(
						null,
						"Не актуален"
				));
			}
			if (customerRepository.findAll().isEmpty()){
				User user = userRepository.findByLogin("overlord");
				Calculation c = calculationRepository.save( new Calculation(
						null,
						"Московское шоссе, 34Б",
						UUID.randomUUID(),
						new Date(System.currentTimeMillis()),
						statusRepository.findByStatusName("Актуален"),
						null
				));

				customerRepository.save(new Customer(
						null,
						"Тестов",
						"Тест",
						"Тестович",
						8005553535L,
						"qwerty@gmail.com",
						"Московское шоссе, 34Б",
						user,
						List.of(calculationRepository.findByNumber(c.getNumber()))
				));
			}
		};
	}
}

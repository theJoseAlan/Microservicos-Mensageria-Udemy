package io.github.cursodesousa.mscloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

//Isso aqui vai servir para que seja obtido uma url estática, assim não vou precisar ficar pegando a porta
//toda vez que subo a aplicação de clientes, já que ele cria uma nova porta a cada execução
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class MscloudgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscloudgatewayApplication.class, args);
	}

	//Vai fazer o roteamento das rotas para o discoveryServer
	/*
	*
	* .route(r -> r.path("/clientes/**").uri("lb://msclientes"))
	* 'r' de rota que será convertido em 'r.path("/clientes/**")' para definir uma rota
	* os dois ** indicam que depois do caminho /clientes/ pode ser qualquer coisa (tipo um cpf para dar um
	* findByCpf ou outra coisa)
	* depois redireciona para o loadbalancer (lb) para o microsserviço de clientes.
	* lb://'aqui vem o nome do microservice, que pode ser encontrado no aplication.yml'
	*
	* */
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder){
		return builder
				.routes()
				.route(r -> r.path("/clientes/**").uri("lb://msclientes")) //rota para clientes
				.route(r -> r.path("/cartoes/**").uri("lb://mscartoes")) //rota para cartoes
				.build();
	}

}

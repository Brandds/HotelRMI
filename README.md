# HotelRMI

Servidor: O servidor é responsável por oferecer os serviços relacionados à gestão das reservas. Ele usa RMI para expor suas funcionalidades para os clientes. O servidor possui a classe UsuarioServiceImpl, que implementa a interface UsuarioService, e gerencia as operações de cadastro de clientes, consulta de quartos, reservas, e outras funcionalidades.

O servidor se conecta a um banco de dados MySQL, onde as informações sobre quartos, reservas e clientes são armazenadas. As tabelas principais são:
->usuario para armazenar informações sobre os clientes.
->quarto para armazenar dados sobre os quartos disponíveis no hotel.
->reserva para armazenar as informações das reservas feitas pelos clientes.

Cliente: O cliente se conecta ao servidor via RMI e interage com o usuário, oferecendo um menu com opções de consulta e manipulação de reservas. O cliente chama os métodos do servidor para executar as operações e exibir os resultados para o usuário.

Para ter acesso ao banco encontra-se a conexão com o banco no arquivo UsuarioServiceImpl no construtor da classe, la voce mudara caso precise, pra ter os scripts de criação das tabelas, popular o banco ir na pasta text que esta dentro da pasta bin, la tera os scripts para criar as tabelas e inserir os dados e também alguns cenarios

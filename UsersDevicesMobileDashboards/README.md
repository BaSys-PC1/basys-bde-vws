# BDE-VWS

FuE Projekt BDE-VWS

# Packages

## basyx.bdevws
Contains the BaSys registry server components to start a BDE-VWS user and device registry and the eyeled setup server.

### Java Main
de.eyeled.fue.basyx.server.backend.BasysRegistryServer


## basyx.bdevws.aas
Contains the BdeVws AAS data code.


## basyx.bdevws.notifications
Contains the BdeVws notification implementation


## bde-vws-docker
Contains the eyeled backend docker environment for the BDE-VWS project. It's separated into four different parts:

1. Eyeled BaSys: Contains the server functionality to start and host the user AAS and device AAS (ie. tablets, mobile phones). It also contains the main entry point for the __BDE-VWS dashboard app__ to receive information for accessing all needed server components. An optional BaSys registry server which can be used to register asset administration shells is also available.

2. Eyeled MMW: The Eyled (messaging) middle-ware contains the core functionality to receive and analyse notifications sent by the DFKI server components. These notifications will then be transfered to a relevant user (device). This middle-ware is also used as a relay to transfer signal data from Kafka to a device via web-sockets.

3. Eyeled DB: The Eyeled DB is a postgres container solely used for notification data.

4. Adminer: The adminer container is a web UI for accessing the Eyeled DB. It can be disabled if not needed.

### Installation
To install the Eyeled BDE-VWS docker container please see the following instructions:

#### 1. Set up environment parameter
It's mandatory to set up the Eyeled environment parameter file (.env), specifically the IP address used for accessing the Eyeled backend components. This IP address is used for AAS registration and as the main entry point used by the dashboard app. Inside the env file we have five mandatory parameter which need to be changed:

1. MAIN_HOST: The main host IP address used for user and device AAS. 
2. BDE_HOST_ADDRESS: The complete URL of the eyeled MMW server used to transfer Kafka signal data to the dashboard app. 
3. NOTIFICATION_HOST_ADDRESS: The complete web socket URL of the Eyeled notification MMW server.
4. KAFKA_HOST_ADDRESS: The kafka host address including the port.
5. DB_HOST_ADDRESS: The Eyeled DB server address used to save the notification data.

Following is a complete list of available env parameter to customize the server entry points:

##### Environment Parameter (.env)
```
MAIN_HOST: The IP address of the running server 
REGISTRY_HOST: If the registry host parameter is set and the eyeled registry server is activated, the main host parameter will be overridden and the eyeled registry server will start using the given IP address. If the eyeled registry server is deactivated the registry host parameter should be used to link to an external AAS registry server (using the full URL)
REGISTRY_PORT: If the registry port parameter is set and the eyeled registry server is activated, this port will be used to start the eyeled registry server. (default: 4999)
DEACTIVATE_REGISTRY: If true the eyeled AAS registry server won't start. In this case the "REGISTRY_HOST" parameter must be set. (default: false)
REGISTRY_TYPE: If the eyeled registry server is activated this parameter sets the default registry type (ie IN_MEMORY or SQL) (default: IN_MEMORY)
REGISTRY_SERVLETS: If the eyeled registry server is activated this parameter allows customized registry servlets which will be started beside the device and user servlet.  
BDE_HOST_ADDRESS: The complete host address of the eyeled middle-ware server. This address will be used to request signal information via web sockets.
NOTIFICATION_HOST_ADDRESS: the notification server host address  
KAFKA_HOST_ADDRESS: the kafka host address including the port
DB_HOST_ADDRESS: the database server used for notifications
POSTGRES_PASSWORD: the postgres user password to access the DB_HOST_ADDRESS
POSTGRES_USER: the postgres user name to access the DB_HOST_ADDRESS
POSTGRES_DB: the postgres db name used to save the notifications
USER_HOST: If set the "MAIN_HOST" parameter will be overridden to start the user AAS using the given IP parameter.
USER_PORT: If set the default user AAS port will be overridden. (default 4000)
DEVICE_HOST: If set the "MAIN_HOST" parameter will be overridden to start the device AAS using the given IP parameter.
DEVICE_PORT: If set the default device AAS port will be overridden. (default 4010)
```

__Example 1:__

The following example deactivates the internal eyeled registry server and points to an external AAS registry server. The user AAS components will use the ip address given by the MAIN_HOST parameter.
```
MAIN_HOST=192.168.0.213
REGISTRY_HOST=http://192.168.0.213:4999
DEACTIVATE_REGISTRY=true
BDE_HOST_ADDRESS=http://192.168.0.213:8080/eye.betriebsdatenserver/Betriebsdaten
KAFKA_HOST_ADDRESS=192.168.0.213:9092
DB_HOST_ADDRESS=192.168.0.213:5432
POSTGRES_PASSWORD=postgres
POSTGRES_USER=postgres
POSTGRES_DB=basys_notifications
NOTIFICATION_HOST_ADDRESS=ws://192.168.0.213:8080/eye.betriebsdatenserver/websocket/notifications
```

__Example 2:__

The following example starts the internal eyeled AAS registry server. The registry server IP address is given by the MAIN_HOST parameter and the custom port by the REGISTRY_PORT parameter. As the REGISTRY_SERVLETS parameter is set this will also add the given servlet mappings.
```
MAIN_HOST=192.168.0.213
REGISTRY_TYPE=IN_MEMORY
REGISTRY_PORT=4100
REGISTRY_SERVLETS=test|other
BDE_HOST_ADDRESS=http://192.168.0.213:8080/eye.betriebsdatenserver/Betriebsdaten
KAFKA_HOST_ADDRESS=192.168.0.213:9092
DB_HOST_ADDRESS=192.168.0.213:5432
POSTGRES_PASSWORD=postgres
POSTGRES_USER=postgres
POSTGRES_DB=basys_notifications
NOTIFICATION_HOST_ADDRESS=ws://192.168.0.213:8080/eye.betriebsdatenserver/websocket/notifications
```
The custom registry server servlet parameter will create the following entry points: 

http://192.168.0.213:4100/registry/test/ or 
http://192.168.0.213:4100/registry/other/


#### 2. Set up docker compose file
In rare cases the docker compose file needs to be changed (ie. port conflicts). Currently the docker container use the following ports:

__Eyeled BaSys__
* 4000: the default port used to start the user AAS and for accessing the user setup server
* 4010: the default port used to start the device AAS and for accessing the device setup server
* 4020: the default port used to start the anlagen AAS and for accessing the anlage setup server (optional)
* 4999: the default port for the eyeled AAS registry given by the env REGISTRY_PORT parameter (optional)

__Eyeled MMW__
* 8080: the eyeled mmw server port used (for signal data and notifications)

__Eyeled DB__
* 5432: the db server port

__Adminer__
* 8888: the adminer server port


#### 3. Run docker compose
In a docker environment you can run the eyeled docker components by using the following command:
```
docker-compose build && docker-compose up -d
```

### Endpoint Examples
Following is a list of endpoint examples which can be accessed after installing the docker components.

#### Basys Registry
If activated the eyeled AAS registry can be accessed using the following endpoints:

http://localhost:4999/registry/user/  
http://localhost:4999/registry/device/  

#### User/Device Setup
The setup entry points are used to create user AAS or device AAS. Please see the [user and device setup example](#creating-user-aas-or-device-aas) below for more information.
__User Setup:__ http://localhost:4000/userSetup/  
__Device Setup:__ http://localhost:4010/deviceSetup/ or just http://localhost:4010/



#### AAS
http://localhost:4000/user/idShort/aas/  
http://localhost:4010/device/idShort/aas/  

Example: http://localhost:4000/user/jane_doe/aas/


### Creating User AAS or Device AAS
Currently there is only one way to create a user or device AAS. In order to create a user AAS you have to send Json data to the user setup endpoint. For device AAS objects you have to send Json data to the device setup endpoint. The device AAS will be automatically created as soon as the BDE-VWS dashboard app accesses the device setup URL.

#### Example: Create a user AAS

The following example creates a user called John Doe: 

* The Json array "Benutzer" can contain multiple user objects. 
* The "Stammdaten" "id" needs to be unique. 
* The user password "Passwort" is sha256 encoded.
* The "Image" data can contain a base64 encoded image string.

```
{
	"Benutzer": [
		{
			"Stammdaten": {
				"id": "User4711",
				"Benutzername": "john_doe",
				"Passwort": "ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae",
				"Name": "John Doe",
				"Emailadressen": [
					"john.doe@eyeled.de",
					"john@eyeled.de"
				],
				"Beschreibung": {
					"de-DE": "Benutzer John Doe",
					"en-US": "User John Doe"
				}
			},
			"Mobiles": {},
			"Rollen": [
				"operator",
				"maintainer"
			],
			"Faehigkeiten": [
				"maintaining",
				"monitoring",
				"repairing"
			],
			"Darstellung": [],
			"Image": ""
		}
	]
}
```

Use HTTP POST to send the json user data to the user setup endpoint: http://localhost:4000/userSetup/ 

## basyx.bdevws.app
Contains the apk files for the AAS analyzer and the dashboard app.
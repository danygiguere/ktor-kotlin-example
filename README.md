### About
- just a project to learn Ktor 2

- You can get a free version of IntelliJ IDEA Community edition here `https://www.jetbrains.com/fr-fr/idea/download/#section=mac`

### Installation
- do `cp .env.example .env`
- or create a `.env` file and copy the content from `.env.example`. 
- 
- Then add your db credentials to the `.env` file (the `.env` can't be commited because it's in the .gitignore).
- or to use the in-memory db: go to config/Database and follow the commented instructions

- Then, in two different terminals run these commands from the root of this project: 

    - to activate auto reloading:
    `./gradlew -t installDist`

    - to run the app:
    `./gradlew run`

### Info
- to rerun the db migrations, delete the db tables and rerun the app
- to rerun the db seeds, delete the db tables' content and rerun the app
    
### Demo requirements
#### Completed
- create demo, user and post controllers (demoRoutes, userRoutes, postRoutes)
- create Post model
- create User model
- create db migrations
- create db seeds
- create validation for the post model
- install JWT
- add .env for secrets
- create a middleware
- translate validation
- create demos for logs

#### In progress
- refactor validators. in Validator.kt:
  - @todo: Figure out how to bind the locale to the service container with Koin or Kodein
  - @todo rules : date, before:date, boolean, digits:value, email, in:foo,bar, integer, notIn:foo,bar, regex:pattern, string, requiredIf:anotherfield,value, image:jpeg,png,jpg,gif,svg, unique:users,email
  - @todo: move translation texts to .json files
  - create Rule class and the rules like MaxLength will extend Rule

#### Todo
- create `user has many posts` relationship
- figure out a way to remigrate the db and the seeds : https://medium.com/nerd-for-tech/an-opinionated-kotlin-backend-service-database-migration-orm-52527ce3228
- install cors - https://ktor.io/docs/eap/cors.html#overview
- create demos for joins, db transactions
- create some tests
- figure how to reset the test db before each test
- figure how to run the tests as transactions, so the data is not committed in the db
- install swagger : https://ktor.io/docs/eap/openapi-swagger.html
- implement websockets
- create isAuthenticated middleware
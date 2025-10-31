env = "dev"
region = "us-east-2"

app_prefix = "link-shortener-saas"

lambda_handler = "dev.senna.StreamLambdaHandler::handleRequest"

env_vars = {
  "ENV"= "dev"
  "FEATURE_FLAG_CREATE_USERS" = "false"
  "SECRET_NAME" = "dev-link-shortener-saas-jwt-secret"
  "SPRING_PROFILES_ACTIVE" = "dev"
  "JAVA_TOOL_OPTIONS"="-Dnetworkaddress.cache.ttl=5 -Dnetworkaddress.cache.negative.ttl=0"
}

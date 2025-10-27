env = "dev"
region = "us-east-2"

app_prefix = "link-shortener-saas"

lambda_handler = "dev.senna.StreamLambdaHandler::handleRequest"

env_vars = {
  "SECRET_NAME" = "dev-link-shortener-saas-jwt-secrets"
  "SPRING_PROFILES_ACTIVE" = "dev"
  "JAVA_TOOL_OPTIONS"="-Dnetworkaddress.cache.ttl=5 -Dnetworkaddress.cache.negative.ttl=0"
}

env = "dev"
region = "us-east-2"

app_prefix = "link-shortener-saas"

lambda_handler = "dev.senna.StreamLambdaHandler::handleRequest"

env_vars = {
  "SECRET_NAME" = "dev-link-shortener-saas-jwt-secrets"
}

### Bet Manager

Simple Java System to produce and consume bet details.

### Features

* Reads Bet odds from endpoint `/api/v1/ingest`
* Pushes the bet to Kafka
* A consumer reads the bets
* Consumer saves the bet into the database.
* Get bet details from `/api/v1/bets`
import http from "../http-common";

class EntityDataService {

	getAll() {
		return http.get("/entities");
	}

	get(id) {
		return http.get('/entities/${id}');
	}

	create(data) {
		return http.post("/entities", data);
	}

	update(id, data) {
		return http.put('/entities/${id}', data);
	}

	delete(id) {
		return http.delete('/entities/${id}');
	}

	deleteAll() {
		return http.delete('/entities');
	}

	findByName(name) {
		return http.get('/entities?name=${name}');
	}
}

export default new EntityDataService();
import { createWebHistory, createRouter } from "vue-router";

const routes = [

	{
		path: "/",
		alias: "/entities",
		name: "entities",
		component: () => import("./components/EntitiesList")
	},

	{
		path: "/entities/:id",
		name: "entity-details",
		component: () => import("./components/Entity")
	},

	{
		path: "/add",
		name: "add",
		component: () => import("./components/AddEntity")
	}
];

const router = createRouter({
	history: createWebHistory(),
	routes,
});

export default router;
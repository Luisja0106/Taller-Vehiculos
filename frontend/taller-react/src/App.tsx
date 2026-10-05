import { NotFoundPage } from "@pages/404";
import { EmpleadosDashboard } from "@pages/EmpleadosDashboard";
import { MainDashboard } from "@pages/MainDashboard";
import { useEffect, useState } from "react";

function App() {
	const [currentPath, setCurrentPath] = useState(window.location.pathname);
	let page = <NotFoundPage />;

	if (currentPath === "/") {
		page = <MainDashboard />;
	} else if (currentPath === "/empleados") {
		page = <EmpleadosDashboard />;
	}

	useEffect(() => {
		const handleLocationChange = () => {
			setCurrentPath(window.location.pathname);
		};

		window.addEventListener("popstate", handleLocationChange);

		return () => {
			window.removeEventListener("popstate", handleLocationChange);
		};
	}, []);

	return page;
}
export default App;

import { useCurrentPath } from "@hooks/useCurrentPath";
import { NotFoundPage } from "@pages/404.tsx";
import { EmpleadosDashboard } from "@pages/EmpleadosDashboard.tsx";
import { MainDashboard } from "@pages/MainDashboard.tsx";

function App() {
	const { currentPath } = useCurrentPath();

	let page = <NotFoundPage />;

	if (currentPath === "/") {
		page = <MainDashboard />;
	} else if (currentPath === "/empleados") {
		page = <EmpleadosDashboard />;
	}

	return page;
}
export default App;

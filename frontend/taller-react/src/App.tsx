import { useReader } from "@hooks/useReader.tsx";
import { NotFoundPage } from "@pages/404.tsx";
import { EmpleadosDashboard } from "@pages/EmpleadosDashboard.tsx";
import { MainDashboard } from "@pages/MainDashboard.tsx";

function App() {
	const { currentPath } = useReader();

	let page = <NotFoundPage />;

	if (currentPath === "/") {
		page = <MainDashboard />;
	} else if (currentPath === "/empleados") {
		page = <EmpleadosDashboard />;
	}

	return page;
}
export default App;

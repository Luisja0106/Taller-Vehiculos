import { Route } from "@components/Route";
import { EmpleadosDashboard } from "@pages/EmpleadosDashboard.tsx";
import { MainDashboard } from "@pages/MainDashboard.tsx";

function App() {
	return (
		<>
			<Route path="/" component={MainDashboard} />
			<Route path="/empleados" component={EmpleadosDashboard} />
		</>
	);
}
export default App;

import { popstate } from "@utils/NavigateTo";
import { useEffect, useState } from "react";

export function useReader() {
	const [currentPath, setCurrentPath] = useState(window.location.pathname);

	useEffect(() => {
		const handleLocationChange = () => {
			setCurrentPath(window.location.pathname);
		};

		window.addEventListener(popstate, handleLocationChange);

		return () => {
			window.removeEventListener(popstate, handleLocationChange);
		};
	}, []);

	return {
		currentPath,
	};
}

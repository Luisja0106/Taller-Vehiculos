import { CHANGE_LOCATION_EVENT } from "@utils/NavigateTo";
import { useEffect, useState } from "react";

/**
 * Change the state of current path
 * @returns currentpath which is the actual path
 */
export function useCurrentPath() {
	const [currentPath, setCurrentPath] = useState(window.location.pathname);

	useEffect(() => {
		const handleLocationChange = () => {
			setCurrentPath(window.location.pathname); //it changes the state of the currentpath to the new pathname
		};

		window.addEventListener(CHANGE_LOCATION_EVENT, handleLocationChange); //in case that the url changes it call the function

		return () => {
			window.removeEventListener(CHANGE_LOCATION_EVENT, handleLocationChange); //unsuscribe of the event
		};
	}, []); //for the single [] it only executes once

	return {
		currentPath, //return the new current path
	};
}

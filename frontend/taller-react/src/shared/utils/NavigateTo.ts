export const CHANGE_LOCATION_EVENT = "popstate";

/**
 *Modify the URL and call a event
 *@param path - the new path url
 */
export function NavigateTo(path: string) {
	window.history.pushState({}, "", path); //changes the URL
	window.dispatchEvent(new PopStateEvent(CHANGE_LOCATION_EVENT)); //call the event
}

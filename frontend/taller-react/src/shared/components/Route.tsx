import { useCurrentPath } from "@hooks/useCurrentPath";
import type { ElementType } from "react";

interface RouteProps {
	path: string;
	component: ElementType;
}

export function Route({ path, component: ComponentToRender }: RouteProps) {
	const { currentPath } = useCurrentPath();

	if (currentPath !== path) return null;

	return <ComponentToRender />;
}

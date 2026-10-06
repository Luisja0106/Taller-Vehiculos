import { NavigateTo } from "@utils/NavigateTo";
import type { ComponentProps, MouseEvent, ReactNode } from "react";

interface linkProps extends ComponentProps<"a"> {
	href: string;
	children: ReactNode;
}

export function Link({ href, children, onClick, ...restOfProps }: linkProps) {
	const handleClick = (event: MouseEvent<HTMLAnchorElement>) => {
		onClick?.(event);
		const hasModefierKey =
			event.ctrlKey || event.altKey || event.metaKey || event.shiftKey;

		if (event.defaultPrevented || hasModefierKey) {
			return;
		}
		event.preventDefault();
		NavigateTo(href);
	};
	return (
		<a href={href} {...restOfProps} onClick={handleClick}>
			{children}
		</a>
	);
}

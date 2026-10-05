import type { ComponentProps, MouseEvent, ReactNode } from "react";

interface linkProps extends ComponentProps<"a"> {
	href: string;
	children: ReactNode;
}

export function Link({ href, children, ...restOfProps }: linkProps) {
	const handleClick = (event: MouseEvent<HTMLAnchorElement>) => {
		event.preventDefault();

		window.history.pushState({}, "", href);
		window.dispatchEvent(new PopStateEvent("popstate"));
	};
	return (
		<a href={href} {...restOfProps} onClick={handleClick}>
			{children}
		</a>
	);
}

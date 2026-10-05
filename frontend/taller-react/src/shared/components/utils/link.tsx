import { useRouter } from "@hooks/useRouter.tsx";
import type { ComponentProps, MouseEvent, ReactNode } from "react";

interface linkProps extends ComponentProps<"a"> {
	href: string;
	children: ReactNode;
}

export function Link({ href, children, ...restOfProps }: linkProps) {
	const { navigateTo } = useRouter();

	const handleClick = (event: MouseEvent<HTMLAnchorElement>) => {
		event.preventDefault();
		navigateTo(href);
	};
	return (
		<a href={href} {...restOfProps} onClick={handleClick}>
			{children}
		</a>
	);
}

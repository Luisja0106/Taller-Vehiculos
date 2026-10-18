import type { MouseEvent } from "react";
import { Link } from "./Link";

interface PaginationProps {
	currentPage: number;
	totalPages: number;
	onPageChange: (page: number) => void;
}

export function Pagination({
	currentPage,
	totalPages,
	onPageChange,
}: PaginationProps) {
	const pages = Array.from({ length: totalPages }, (_, i) => i + 1);
	const isFirstPage = currentPage === 1;
	const isLastPage = currentPage === totalPages;

	const stylePrevButton = isFirstPage ? { opacity: 0.5 } : {};
	const styleNextButton = isLastPage ? { opacity: 0.5 } : {};

	const handleNextPage = (e: MouseEvent<HTMLAnchorElement>) => {
		e.preventDefault();
		if (!isLastPage) {
			onPageChange(currentPage + 1);
		}
	};
	const handlePrevClick = (e: MouseEvent<HTMLAnchorElement>) => {
		e.preventDefault();
		if (!isFirstPage) {
			onPageChange(currentPage - 1);
		}
	};
	const handleChangePage = (e: MouseEvent<HTMLAnchorElement>) => {
		e.preventDefault();
		const page = Number(e.currentTarget.dataset.page);
		if (page !== currentPage) {
			onPageChange(page);
		}
	};
	return (
		<nav className="my-8 flex justify-center gap-2">
			<Link
				href="/"
				style={stylePrevButton}
				onClick={handlePrevClick}
				inert={isFirstPage}
				className="flex size-10 items-center justify-center rounded-md text-black no-underline transition-all duration-300 hover:bg-white focus:bg-white"
			>
				<svg
					width="16"
					height="16"
					viewBox="0 0 24 24"
					fill="none"
					stroke="currentColor"
					strokeWidth="2"
					strokeLinecap="round"
					strokeLinejoin="round"
				>
					<title>Pagina Anterior</title>
					<path stroke="none" d="M0 0h24v24H0z" fill="none" />
					<path d="M15 6l-6 6l6 6" />
				</svg>
			</Link>
			{pages.map((page) => {
				const isCurrentPage = currentPage === page;
				return (
					<Link
						href="/"
						key={page}
						aria-label={`Pagina numero ${page}`}
						data-page={page}
						onClick={handleChangePage}
						inert={isCurrentPage}
						className={
							isCurrentPage
								? "pointer-events-none flex size-10 items-center justify-center rounded-md border border-(--accent) text-(--accent) no-underline transition-transform duration-100 ease-in-out"
								: "flex size-10 items-center justify-center rounded-md text-black no-underline transition-transform duration-100 ease-in-out hover:bg-white focus:bg-white"
						}
					>
						{page}
					</Link>
				);
			})}
			<Link
				href="/"
				style={styleNextButton}
				onClick={handleNextPage}
				inert={isLastPage}
				className="flex size-10 items-center justify-center rounded-md text-black no-underline transition-all duration-300 hover:bg-white focus:bg-white"
			>
				<svg
					width="16"
					height="16"
					viewBox="0 0 24 24"
					fill="none"
					stroke="currentColor"
					strokeWidth="1.5"
					strokeLinecap="round"
					strokeLinejoin="round"
					className="icon icon-tabler icons-tabler-outline icon-tabler-chevron-right"
				>
					<title>Pagina Siguiente</title>
					<path stroke="none" d="M0 0h24v24H0z" fill="none" />
					<path d="M9 6l6 6l-6 6" />
				</svg>
			</Link>
		</nav>
	);
}

export default function Home() {
  return (
    <html>
      <head>
        <meta httpEquiv="refresh" content="0;url=./docs" />
      </head>
      <body />
    </html>
  );
}

export async function getConfig() {
  return {
    render: 'static',
  };
}
